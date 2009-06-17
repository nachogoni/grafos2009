
cat mysql/create.sql > temp
for i in $(seq 1 100000);
    do
        echo "INSERT INTO amigos VALUES(NULL, 'aa');" >> temp;
    done
cat mysql/inserts.sql >> temp
cat temp | mysql -u root -p
rm temp

