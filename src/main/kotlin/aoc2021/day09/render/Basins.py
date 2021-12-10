def render():
    file1 = open('../assets/input', 'r')
    lines = file1.readlines()
    matrix = []
    for line in lines:
        line_chars = []
        for c in line:
            line_chars.append(c)
        matrix.append(line_chars)

    with open('basins.html', 'w') as f:
        s = """<!doctype html>
<head>
<meta charset="utf-8">
<link href="//fonts.googleapis.com/css?family=Source+Code+Pro:600&amp;subset=latin,latin-ext" rel="stylesheet" type="text/css">
<style>
    body {
        background: #0f0f23;
        font-weight: 600;
        font-family: "Source Code Pro", monospace;
    }
    .depth-0 {
        background: #01000Dff;
    }
    .depth-1 {
        background: #0E1427ff;
    }
    .depth-2 {
        background: #1A2941ff;
    }
    .depth-3 {
        background: #273D5Bff;
    }
    .depth-4 {
        background: #345175ff;
    }
    .depth-5 {
        background: #40668Eff;
    }
    .depth-6 {
        background: #4D7AA8ff;
    }
    .depth-7 {
        background: #5A8EC2ff;
    }
    .depth-8 {
        background: #66A3DCff;
    }
    .depth-9 {
        background: #73B7F6ff;
    }

</style>
<body>
"""
        for y in matrix:
            line = ''.join(y)
            line = line.replace('0', '<span class="depth-0">&nbsp;&nbsp;</span>')
            line = line.replace('1', '<span class="depth-1">&nbsp;&nbsp;</span>')
            line = line.replace('2', '<span class="depth-2">&nbsp;&nbsp;</span>')
            line = line.replace('3', '<span class="depth-3">&nbsp;&nbsp;</span>')
            line = line.replace('4', '<span class="depth-4">&nbsp;&nbsp;</span>')
            line = line.replace('5', '<span class="depth-5">&nbsp;&nbsp;</span>')
            line = line.replace('6', '<span class="depth-6">&nbsp;&nbsp;</span>')
            line = line.replace('7', '<span class="depth-7">&nbsp;&nbsp;</span>')
            line = line.replace('8', '<span class="depth-8">&nbsp;&nbsp;</span>')
            line = line.replace('9', '<span class="depth-9">&nbsp;&nbsp;</span>')
            s += '  <div>{}</div>\n'.format(line)

        s += '</body>'
        f.write(s)


if __name__ == '__main__':
    render()


