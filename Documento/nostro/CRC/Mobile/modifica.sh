#! /bin/bash

for file in $(ls )
do
    sed -i 's/\textbf{Superclasse}/\\textbf{\textbf{Superclasse}}/g' $file
    sed -i 's/\textbf{Nome Classe}/\\textbf{\textbf{Nome Classe}}/g' $file
    sed -i 's/\textbf{Sottoclassi}/\\textbf{\textbf{Sottoclassi}}/g' $file
    sed -i 's/\textbf{Responsabilità}/\\textbf{\textbf{Responsabilità}}/g' $file
    sed -i 's/\textbf{Collaboratore}/\\textbf{\textbf{Collaboratore}}/g' $file

done