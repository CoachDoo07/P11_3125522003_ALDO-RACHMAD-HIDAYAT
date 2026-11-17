import java.util.ArrayList;
import java.util.List;

class Buku {
    private final String kode;
    private final String judul;

    public Buku(String kode, String judul) {
        if (kode == null || kode.trim().isEmpty() || judul == null || judul.trim().isEmpty()) {
            throw new IllegalArgumentException("Kode dan judul wajib diisi.");
        }
        this.kode = kode.trim();
        this.judul = judul.trim();
    }

    public String getKode() {
        return kode;
    }

    public String getJudul() {
        return judul;
    }
}

class Perpustakaan {
    private final List<Buku> daftarBuku = new ArrayList<>();

    public void tambahBuku(Buku buku) {
        if (buku == null) {
            throw new IllegalArgumentException("Objek buku tidak boleh null.");
        }
        if (cariBuku(buku.getKode()) != null) {
            throw new IllegalArgumentException("Kode buku sudah terdaftar.");
        }
        daftarBuku.add(buku);
    }

    public Buku cariBuku(String kode) {
        if (kode == null) {
            return null;
        }
        for (Buku buku : daftarBuku) {
            if (buku.getKode().equalsIgnoreCase(kode.trim())) {
                return buku;
            }
        }
        return null;
    }

    public boolean hapusBuku(String kode) {
        Buku buku = cariBuku(kode);
        if (buku == null) {
            return false;
        }
        return daftarBuku.remove(buku);
    }

    public void tampilkanBuku() {
        if (daftarBuku.isEmpty()) {
            System.out.println("Koleksi masih kosong.");
            return;
        }
        for (Buku buku : daftarBuku) {
            System.out.println(buku.getKode() + " - " + buku.getJudul());
        }
        System.out.println("Total buku: " + daftarBuku.size());
    }

    public int getJumlahBuku() {
        return daftarBuku.size();
    }
}

public class Main {
    public static void main(String[] args) {
        System.out.println("=== PENGUJIAN MODUL 11: KOLEKSI BUKU PERPUSTAKAAN ===");
        Perpustakaan perpustakaan = new Perpustakaan();

        // Skenario 1: Tampilkan koleksi saat masih kosong
        System.out.println("\n[Skenario 1] Tampilkan koleksi awal (kosong)");
        perpustakaan.tampilkanBuku();
        System.out.println("Jumlah buku: " + perpustakaan.getJumlahBuku());

        // Skenario 2: Tambah B001 dan B002
        System.out.println("\n[Skenario 2] Tambah B001 dan B002");
        try {
            perpustakaan.tambahBuku(new Buku("B001", "Dasar Java"));
            perpustakaan.tambahBuku(new Buku("B002", "Konsep OOP"));
            System.out.println("Kedua buku berhasil tersimpan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ditolak: " + e.getMessage());
        }
        perpustakaan.tampilkanBuku();

        // Skenario 3: Cari B002
        System.out.println("\n[Skenario 3] Cari B002");
        Buku hasilB002 = perpustakaan.cariBuku("B002");
        System.out.println(hasilB002 != null ? "Ditemukan: " + hasilB002.getJudul() : "Buku tidak ditemukan.");
        System.out.println("Jumlah buku: " + perpustakaan.getJumlahBuku());

        // Skenario 4: Tambah B001 kembali (duplikat)
        System.out.println("\n[Skenario 4] Tambah B001 kembali (kode duplikat)");
        try {
            perpustakaan.tambahBuku(new Buku("B001", "Buku Duplikat"));
            System.out.println("Buku berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ditolak: " + e.getMessage());
        }
        System.out.println("Jumlah buku: " + perpustakaan.getJumlahBuku());

        // Skenario 5: Pengujian judul kosong
        System.out.println("\n[Skenario 5] Tambah buku dengan judul kosong");
        try {
            perpustakaan.tambahBuku(new Buku("B003", "   "));
            System.out.println("Buku berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ditolak: " + e.getMessage());
        }
        System.out.println("Jumlah buku: " + perpustakaan.getJumlahBuku());

        // Skenario 6: Pengujian objek null
        System.out.println("\n[Skenario 6] Tambah objek buku null");
        try {
            perpustakaan.tambahBuku(null);
            System.out.println("Buku berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ditolak: " + e.getMessage());
        }
        System.out.println("Jumlah buku: " + perpustakaan.getJumlahBuku());

        // Skenario 7: Hapus B001
        System.out.println("\n[Skenario 7] Hapus B001");
        boolean statusHapusB001 = perpustakaan.hapusBuku("B001");
        System.out.println("Penghapusan B001: " + statusHapusB001);
        perpustakaan.tampilkanBuku();

        // Skenario 8: Cari B001 setelah dihapus
        System.out.println("\n[Skenario 8] Cari B001 (setelah dihapus)");
        Buku hasilB001 = perpustakaan.cariBuku("B001");
        System.out.println(hasilB001 != null ? "Ditemukan: " + hasilB001.getJudul() : "Buku tidak ditemukan (null).");
        System.out.println("Jumlah buku: " + perpustakaan.getJumlahBuku());

        // Skenario 9: Hapus B999 (tidak ada)
        System.out.println("\n[Skenario 9] Hapus B999 (kode tidak terdaftar)");
        boolean statusHapusB999 = perpustakaan.hapusBuku("B999");
        System.out.println("Penghapusan B999: " + statusHapusB999);
        System.out.println("Jumlah buku: " + perpustakaan.getJumlahBuku());

        // Skenario 10: Hapus sisa buku (B002) dan tampilkan koleksi kosong
        System.out.println("\n[Skenario 10] Hapus B002 hingga koleksi kosong");
        perpustakaan.hapusBuku("B002");
        perpustakaan.tampilkanBuku();
        System.out.println("Jumlah buku: " + perpustakaan.getJumlahBuku());
    }
}
