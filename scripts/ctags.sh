#!/bin/bash

ctags -f .ctags -R --language-force=java src
echo "now start vi/vim and type ':set tags=.ctags' then CTRL+] and CTRL+t to navigate (and CTRL+P for completion)"
