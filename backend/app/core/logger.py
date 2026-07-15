import logging
import json
import time
from datetime import datetime
from starlette.middleware.base import BaseHTTPMiddleware
from fastapi import Request

class JsonFormatter(logging.Formatter):
    def format(self, record):
        log_record = {
            "time": datetime.utcnow().isoformat() + "Z",
            "level": record.levelname,
            "message": record.getMessage(),
            "name": record.name
        }
        if hasattr(record, "request_id"):
            log_record["request_id"] = record.request_id
        if record.exc_info:
            log_record["exc_info"] = self.formatException(record.exc_info)
        return json.dumps(log_record)

def setup_logger():
    logger = logging.getLogger("autocrm_backend")
    logger.setLevel(logging.INFO)
    
    # Prevent duplicate logs if already configured
    if not logger.handlers:
        handler = logging.StreamHandler()
        handler.setFormatter(JsonFormatter())
        logger.addHandler(handler)
        
    # Also override uvicorn access logs
    uvicorn_logger = logging.getLogger("uvicorn.access")
    uvicorn_logger.handlers = logger.handlers
    uvicorn_logger.propagate = False
    
    return logger

logger = setup_logger()

class LoggingMiddleware(BaseHTTPMiddleware):
    async def dispatch(self, request: Request, call_next):
        start_time = time.time()
        
        response = None
        status_code = 500
        try:
            response = await call_next(request)
            status_code = response.status_code
        except Exception as e:
            logger.error(f"Request failed with exception: {str(e)}", exc_info=True)
            raise e
        finally:
            process_time = (time.time() - start_time) * 1000
            
            # Skip logging health checks to avoid noise
            if request.url.path != "/health":
                logger.info(
                    f"Request {request.method} {request.url.path} completed in {process_time:.2f}ms with status {status_code}",
                    extra={
                        "method": request.method,
                        "path": request.url.path,
                        "status_code": status_code,
                        "latency_ms": round(process_time, 2)
                    }
                )
                
        return response
