#rm coordFile.txt
rm $1.xlsx
python PrefAttachment.py $1 $2
python writeExcel.py $1.txt $2