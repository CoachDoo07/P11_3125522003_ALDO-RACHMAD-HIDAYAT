# P11_3125522003_ALDO RACHMAD HIDAYAT

## Tugas Praktikum P11 - Pemrograman Berorientasi Obyek
**Topik:** Collection dan Pengelolaan Kumpulan Objek  
**Nama Mahasiswa:** Aldo Rachmad Hidayat  
**NRP:** 3125522003  
**Program Studi:** D3 Teknik Informatika - PENS PSDKU Sumenep  
**Dosen Pengampu:** Nirwana Haidar Hari, S.Pd., M.Kom.  
**Project:** SiKos - Sistem Informasi Manajemen Kos (Lanjutan P10)

---

## 1. Deskripsi Proyek & Konsep Collection
Pada praktikum P11, sistem **SiKos** dikembangkan lebih lanjut dengan mengadopsi **Java Collections Framework**, khususnya `List` dan `ArrayList` dengan *Generics* (`List<Kamar>` dan `List<Penyewa>`). 

Penerapan ini menggantikan penyimpanan referensi tunggal atau array statis menjadi kumpulan objek dinamis yang aman, terstruktur, dan mengenkapsulasi seluruh operasi manipulasi data.

### Operasi Utama pada Class `Kos`:
1. **Tambah Data (`add`)**:
   - `tambahKamar(Kamar k)` & `tambahKamar(nomor, tipe, harga)`: Menolak objek `null` dan mendeteksi nomor kamar duplikat (`IllegalArgumentException`).
   - `daftarkanPenyewa(id, nama, kamar, tgl)`: Memeriksa keunikan ID penyewa **sebelum** kamar ditempati agar konsistensi state kamar tidak rusak jika pendaftaran ditolak.
2. **Pencarian Data (`find`)**:
   - `cariKamar(String no)`: Mencari objek kamar berdasarkan nomor kamar unik menggunakan iterasi `for-each` (case-insensitive & trim).
   - `cariPenyewa(String id)`: Mencari data penyewa berdasarkan ID unik.
   - `cariKamarKosong()`: Menemukan kamar pertama yang berstatus tersedia (`isStatusTersedia() == true`).
3. **Penghapusan Data (`remove`)**:
   - `hapusKamar(String no)`: Menghapus kamar dari list. **Aturan Bisnis:** Kamar yang sedang dihuni (`!k.isStatusTersedia()`) **dilarang dihapus** dan melempar `IllegalStateException`.
   - `hapusPenyewa(String id)`: Menghapus penyewa dari list. **Aturan Bisnis:** Penyewa yang masih aktif menempati kamar (`p.isAktif() == true`) **dilarang dihapus** sebelum proses check-out diselesaikan.
4. **Menampilkan Data (`display`)**:
   - `tampilkanDaftarKamar()` & `tampilkanDaftarPenyewa()`: Memeriksa kekosongan list (`isEmpty()`), menampilkan rincian tiap objek, dan menghitung total elemen (`size()`).

---

## 2. Struktur Direktori Proyek
```text
P11_3125522003_ALDO RACHMAD HIDAYAT/
├── .gitattributes
├── README.md                          # Dokumentasi lengkap tugas P11
├── README.pdf                         # Dokumen laporan tugas P11 (3 halaman)
├── UML-SiKos-P11.pdf                  # Diagram UML P11 (Use Case, Activity, Sequence, Class)
├── latihan/                           # Implementasi latihan modul (Perpustakaan & Buku)
│   ├── Buku.class
│   ├── Main.class
│   ├── Main.java
│   └── Perpustakaan.class
└── src/                               # Implementasi utama proyek SiKos P11
    ├── DapatDicekStatus.class
    ├── DapatDicekStatus.java
    ├── Kamar.class
    ├── Kamar.java
    ├── KamarTidakTersediaException.class
    ├── KamarTidakTersediaException.java
    ├── Kos.class
    ├── Kos.java
    ├── Main.class
    ├── Main.java
    ├── Pembayaran.class
    ├── Pembayaran.java
    ├── PembayaranTidakCukupException.class
    ├── PembayaranTidakCukupException.java
    ├── PemilikKos.class
    ├── PemilikKos.java
    ├── Pengguna.class
    ├── Pengguna.java
    ├── Penyewa.class
    ├── Penyewa.java
    ├── Tagihan.class
    ├── Tagihan.java
    ├── TagihanBelumLunasException.class
    └── TagihanBelumLunasException.java
```

---

## 3. Tabel Pengujian Proyek SiKos (17 Skenario)

| No | Input / Skenario | Hasil yang Diharapkan | Hasil Aktual | Jumlah Data Akhir |
|:---:|:---|:---|:---|:---:|
| **T1** | Tambah K01 (Single) dan K02 (Double) | Berhasil, kedua kamar tersimpan | BERHASIL - Kamar K01 & K02 ditambahkan | Kamar: 2 \| Penyewa: 0 |
| **T2** | Tambah kamar K01 duplikat | Ditolak: `IllegalArgumentException` | DITOLAK - Nomor K01 sudah terdaftar | Kamar: 2 (tetap) |
| **T3** | Tambah K04 harga <= 0 (-100000) | Ditolak: `IllegalArgumentException` | DITOLAK - Harga sewa harus > 0 | Kamar: 2 (tetap) |
| **T4** | Cari kamar K02 di koleksi | Ditemukan objek Kamar K02 | DITEMUKAN - K02 (Double, Tersedia) | Kamar: 2 |
| **T5** | Cari kamar K99 (tidak terdaftar) | Menghasilkan `null` | TIDAK DITEMUKAN (null) | Kamar: 2 |
| **Info**| Tambah kamar K03 (Single, 500000) | Berhasil tersimpan | Kamar K03 berhasil ditambahkan | Kamar: 3 |
| **T6** | Daftarkan Penyewa Budi (P01) di K01 | Berhasil, Budi tercatat, K01 terisi | BERHASIL - Budi menempati K01 | Kamar: 3 \| Penyewa: 1 |
| **T7** | Tambah Penyewa ID duplikat P01 | Ditolak; K03 tidak ikut terisi | DITOLAK - ID P01 sudah terdaftar | Penyewa: 1 \| K03: Tersedia |
| **T8** | Cari Penyewa P01 | Ditemukan objek Penyewa Budi | DITEMUKAN - Budi (Menempati K01) | Penyewa: 1 |
| **T9** | Hapus K01 (sedang dihuni Budi) | Ditolak: `IllegalStateException` | DITOLAK - Kamar K01 sedang terisi! | Kamar: 3 (tidak berkurang) |
| **T10**| Hapus K02 (kamar kosong/tersedia) | Berhasil dihapus (`return true`) | BERHASIL - Kamar K02 terhapus | Kamar: 2 |
| **T11**| Hapus K99 (tidak terdaftar) | Gagal / `return false` | Kamar tidak ditemukan (false) | Kamar: 2 (tetap) |
| **T12**| Hapus Penyewa P01 (masih aktif) | Ditolak: `IllegalStateException` | DITOLAK - Penyewa Budi masih aktif! | Penyewa: 1 (tetap) |
| **T13**| Bayar tagihan Budi (Rp 300rb & 500rb) | 300rb ditolak; 500rb lunas | Kurang ditolak; 500rb lunas | Tagihan Budi = Lunas |
| **T14**| Budi check-out | Berhasil, K01 dikosongkan | BERHASIL - K01 kosong, Budi nonaktif | K01 = Tersedia |
| **T15**| Hapus Penyewa P01 pasca check-out | Berhasil dihapus (`return true`) | BERHASIL - P01 dihapus dari riwayat | Penyewa: 0 |
| **T16**| Hapus Kamar K01 & K03 (kosong) | Berhasil dihapus | BERHASIL - K01 & K03 terhapus | Kamar: 0 |
| **T17**| Tampilkan saat koleksi kosong | Mencetak pesan informatif | Cetak pesan 'Belum ada data terdaftar' | Kamar: 0 \| Penyewa: 0 |

---

## 4. Tabel Pengujian Latihan Modul (Koleksi Buku Perpustakaan)

| No | Skenario Pengujian | Hasil yang Diharapkan | Hasil Aktual | Jumlah Data |
|:---:|:---|:---|:---|:---:|
| 1 | Tampilkan koleksi awal | Koleksi masih kosong. | Koleksi masih kosong. | 0 |
| 2 | Tambah B001 & B002 | Kedua buku tersimpan | Kedua buku berhasil tersimpan. | 2 |
| 3 | Cari B002 | Judul 'Konsep OOP' ditemukan | Ditemukan: Konsep OOP | 2 |
| 4 | Tambah B001 duplikat | Ditolak karena kode duplikat | Ditolak: Kode buku sudah terdaftar. | 2 (tetap) |
| 5 | Tambah judul kosong | Ditolak `IllegalArgumentException` | Ditolak: Kode dan judul wajib diisi. | 2 (tetap) |
| 6 | Tambah objek buku null | Ditolak `IllegalArgumentException` | Ditolak: Objek buku tidak boleh null. | 2 (tetap) |
| 7 | Hapus B001 | Menghasilkan true | Penghapusan B001: true | 1 |
| 8 | Cari B001 setelah dihapus | Menghasilkan null | Buku tidak ditemukan (null). | 1 |
| 9 | Hapus B999 (tidak ada) | Menghasilkan false | Penghapusan B999: false | 1 (tetap) |
| 10 | Hapus B002 hingga kosong | Koleksi kembali kosong (0) | Koleksi masih kosong. | 0 |

---

## 5. Jawaban Pertanyaan Evaluasi Modul 11

### 1. Apa perbedaan `Buku` dan `List<Buku>`?
* **`Buku`** adalah class model domain yang merepresentasikan satu entitas/objek tunggal buku di dunia nyata (memiliki atribut seperti `kode` dan `judul`).
* **`List<Buku>`** adalah interface struktur data collection generics yang merepresentasikan wadah/kumpulan dari banyak objek bertipe `Buku`. `List` menyediakan operasi dinamis untuk memanipulasi banyak objek sekaligus seperti `add()`, `remove()`, `size()`, dan `isEmpty()`.

### 2. Mengapa indeks list kurang tepat dijadikan identitas tetap sebuah buku?
Karena indeks pada `ArrayList` bersifat **posisi dinamis/relatif**, bukan identitas intrinsik data. Ketika salah satu elemen dihapus menggunakan `remove()`, elemen-elemen setelahnya akan bergeser ke kiri sehingga nomor indeksnya berubah. Jika program mengacu pada indeks sebagai identitas, pemanggilan data di kemudian hari akan menunjuk ke objek yang salah. Identitas unik tetap harus menggunakan atribut unik domain seperti `kode` buku atau `nomorKamar`.

### 3. Mengapa pengecekan kode duplikat dilakukan sebelum `add()`?
Sesuai prinsip **periksa dulu, ubah belakangan** (*validasi mendahului mutasi*). Jika data dimasukkan lebih dulu ke dalam list baru kemudian diperiksa, maka data tidak valid sempat masuk dan merusak integritas collection. Jika terjadi exception di tengah jalan, proses rollback manual akan sangat rentan terhadap inkonsistensi data. Dengan memeriksa keunikan sebelum `add()`, collection dijamin selalu berada dalam state yang konsisten dan valid.

### 4. Apa risiko jika collection internal dapat diubah langsung oleh class lain?
Terjadi kebocoran enkapsulasi (*encapsulation leak*). Pihak luar (seperti class `Main`) dapat memanipulasi list secara tidak terkendali (misalnya memanggil `daftarKamar.clear()`, menyisipkan objek `null`, atau memasukkan data duplikat tanpa validasi). Selain itu, aturan bisnis penting—seperti larangan menghapus kamar yang sedang berpenghuni—akan terlewati begitu saja jika collection internal diekspos secara bebas tanpa melalui method pengontrol di class `Kos`.
