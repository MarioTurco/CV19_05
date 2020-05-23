#! /bin/bash

for file in $(ls)
do
    sed -i 's/Superclasse/\\textbf{Superclasse}/g' $file
    sed -i 's/Nome Classe/\\textbf{Nome Classe}/g' $file
    sed -i 's/Sottoclassi/\\textbf{Sottoclassi}/g' $file
    sed -i 's/Responsabilità/\\textbf{Responsabilità}/g' $file
    sed -i 's/Collaboratore/\\textbf{Collaboratore}/g' $file

done