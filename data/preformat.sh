#!/bin/bash
# Example usage:
# ./preformat.sh shuttle/shuttle.hdr shuttle/shuttle.t*

ARFF_HEADER_FILE=$1
for f in ${@:2:$#}
do
	echo "Processing $f file..."
	cp -f $f $f.arff.tmp
	sed -i "s/\ /,/g" $f.arff.tmp
	cat < $ARFF_HEADER_FILE > $f.arff
	cat < $f.arff.tmp >> $f.arff
	rm $f.arff.tmp
done
