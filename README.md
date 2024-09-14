# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.

The instruction to run PMD:

$ curl -OL https://github.com/pmd/pmd/releases/download/pmd_releases%2F7.5.0/pmd-dist-7.5.0-bin.zip
$ unzip pmd-dist-7.5.0-bin.zip
$ alias pmd="$HOME/pmd-bin-7.5.0/bin/pmd"
$ pmd check -d /src -R rulesets/java/quickstart.xml -f text
