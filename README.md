# _Word Search Puzzle_ Solver Menggunakan Algoritma _Brute Force_
## Deskripsi Singkat
_Word search puzzle_ adalah suatu teka-teki / permainan kata dengan tujuan mencari kata-kata yang tersembunyi dalam kumpulan huruf acak pada suatu bidang permainan ("papan") berbentuk segi empat. Kata-kata tersebut dapat ditemukan dalam 8 arah pembacaan yaitu ke kanan, ke kiri, ke atas, ke bawah, diagonal ke kanan atas, diagonal ke kanan bawah, diagonal ke kiri atas, dan diagonal ke kiri bawah. 

Program ini bertujuan untuk menyelesaikan _word search puzzle_ menggunakan algoritma _brute force_ yang ditulis dalam bahasa pemrograman Java. Pertama-tama, program akan membaca konfigurasi papan permainan (matriks huruf) dan _list_ kata yang akan dicari dari sebuah _text file_ (.txt). Kemudian program akan melakukan pencarian semua kata pada _list_ tersebut dengan mencocokkan huruf demi huruf. Pada akhir program, akan ditampilkan papan puzzle yang sudah "diwarnai" untuk menandakan lokasi kata-kata yang sudah ditemukan. Selain itu juga ditampilkan statistik dari program berupa waktu dan jumlah perbandingan huruf yang diperlukan untuk pencarian kata baik untuk setiap kata maupun secara keseluruhan.

## Requirement Program
1. Program ini dapat dijalankan pada sistem operasi Windows
2. Program ini dapat dijalankan melalui Command Prompt (cmd), Windows Powershell, dan Windows Subsystem for Linux (WSL)

## Cara Menggunakan Program
1. Buka terminal lalu ubah directory ke folder src pada program ini
2. Untuk meng-_compile_ file WordSearchPuzzle, ketikkan `javac -d ../bin/WordSearchPuzzle.java`
3. Ubah directory ke folder bin dengan mengetikkan `cd ../bin`
4. Untuk menjalankan program, ketik `java WordSearchPuzzle` pada terminal
5. Setelah program dijalankan, user akan diminta memasukkan _path_ file .txt yang berisi puzzle yang akan dipecahkan. Misalnya: ketik `..\test\test1-small.txt` untuk menyelesaikan puzzle yang berada pada file test1-small.txt.
6. Hasil penyelesaian _puzzle_ akan ditampilkan ke layar

## Author
Hansel Valentino Tanoto

K-01 / 13520046
