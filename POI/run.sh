#!/bin/sh

java -cp bin:lib/dom4j-1.6.1.jar:lib/poi-3.9-20121203.jar:lib/poi-ooxml-3.9-20121203.jar:lib/poi-ooxml-schemas-3.9-20121203.jar:lib/xmlbeans-2.3.0.jar adjmat/$1 src/data/$2.txt 
