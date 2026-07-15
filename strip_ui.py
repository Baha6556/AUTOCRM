import os
import re

files_to_strip = [
    r"c:\Desktop\AutoCRM\app\src\main\java\com\autocrm\ui\cars\CarsScreen.kt",
    r"c:\Desktop\AutoCRM\app\src\main\java\com\autocrm\ui\cars\CarDetailScreen.kt",
    r"c:\Desktop\AutoCRM\app\src\main\java\com\autocrm\ui\dashboard\DashboardScreen.kt"
]

for filepath in files_to_strip:
    if not os.path.exists(filepath):
        continue
    with open(filepath, 'r', encoding='utf-8') as f:
        lines = f.readlines()
    
    new_lines = []
    for line in lines:
        if line.strip().startswith("@Composable") or line.strip().startswith("@OptIn(ExperimentalMaterial3Api::class)"):
            break
        new_lines.append(line)
        
    with open(filepath, 'w', encoding='utf-8') as f:
        f.writelines(new_lines)
    print(f"Stripped {filepath}")

