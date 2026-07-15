import os

def fix_compose_imports(filepath):
    with open(filepath, 'r', encoding='utf-8') as f:
        content = f.read()

    if "androidx.compose.runtime.*" in content or "androidx.compose.runtime" in content:
        if "import androidx.compose.runtime.getValue" not in content:
            # find where import androidx.compose.runtime is and add it
            content = content.replace("import androidx.compose.runtime.*", "import androidx.compose.runtime.*\nimport androidx.compose.runtime.getValue\nimport androidx.compose.runtime.setValue")
            with open(filepath, 'w', encoding='utf-8') as f:
                f.write(content)
            print(f"Fixed {filepath}")

ui_dir = r"c:\Desktop\AutoCRM\app\src\main\java\com\autocrm\presentation\ui"
for root, dirs, files in os.walk(ui_dir):
    for file in files:
        if file.endswith(".kt"):
            fix_compose_imports(os.path.join(root, file))
