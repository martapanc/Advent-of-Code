def render():
    file1 = open('input_for_render', 'r')
    lines = file1.readlines()
    matrix = []
    for line in lines:
        line_chars = []
        for c in line:
            line_chars.append(c)
        matrix.append(line_chars)

    with open('sea.html', 'w') as f:
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
    .light-sea {
        background-color: royalblue;
    }
    .sea {
        color: #244383;
    }
    .sea-monster {
        color: #006400;
    }
</style>
<body>
"""
        for y in matrix:
            line = ''.join(y)
            line = line.replace('#', '<span class="sea">~</span>')
            line = line.replace('.', '<span class="light-sea">&nbsp;</span>')
            line = line.replace('@', '<span class="sea-monster">@</span>')
            s += '  <div>{}</div>\n'.format(line)

        s += '</body>'
        f.write(s)


if __name__ == '__main__':
    render()


