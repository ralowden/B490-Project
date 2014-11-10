javac -d POI/bin -sourcepath .:POI/src -cp POI/lib/dom4j-1.6.1.jar:POI/lib/poi-3.9-20121203.jar:POI/lib/poi-ooxml-3.9-20121203.jar:POI/lib/poi-ooxml-schemas-3.9-20121203.jar:POI/lib/xmlbeans-2.3.0.jar POI/src/adjmat/ReadExcel.java; rm POI/src/adjmat/*~; rm *~;
java -cp .:POI/bin:POI/lib/dom4j-1.6.1.jar:POI/lib/poi-3.9-20121203.jar:POI/lib/poi-ooxml-3.9-20121203.jar:POI/lib/poi-ooxml-schemas-3.9-20121203.jar:POI/lib/xmlbeans-2.3.0.jar adjmat/ReadExcel $1 $2 $3 $4 $5 $6


