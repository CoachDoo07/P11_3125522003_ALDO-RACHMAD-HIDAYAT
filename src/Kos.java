import java.util.ArrayList;
import java.util.List;

public class Kos {
    private String namaKos;
    // Menggunakan List dan ArrayList (Generics) untuk menyimpan kumpulan objek domain
    private final List<Kamar> daftarKamar = new ArrayList<>();
    private final List<Penyewa> daftarPenyewa = new ArrayList<>();

    public Kos(String namaKos) {
        if (namaKos == null || namaKos.isBlank()) {
            throw new IllegalArgumentException("Nama kos tidak boleh kosong");
        }
        this.namaKos = namaKos.trim();
    }

    public String getNamaKos() {
        return namaKos;
    }

    // =========================================================
    // PENGELOLAAN KOLEKSI KAMAR (Tambah, Cari, Tampilkan, Hapus)
    // =========================================================

    public int getJumlahKamar() {
        return daftarKamar.size();
    }

    // 1. TAMBAH KAMAR (Overloading 1)
    public void tambahKamar(Kamar kamar) {
        if (kamar == null) {
            throw new IllegalArgumentException("Objek kamar tidak boleh null");
        }
        if (cariKamar(kamar.getNomorKamar()) != null) {
            throw new IllegalArgumentException("Nomor kamar " + kamar.getNomorKamar()
                    + " sudah terdaftar di " + namaKos);
        }
        daftarKamar.add(kamar);
    }

    // TAMBAH KAMAR (Overloading 2 dari data mentah)
    public void tambahKamar(String nomorKamar, String tipeKamar, double hargaSewa) {
        tambahKamar(new Kamar(nomorKamar, tipeKamar, hargaSewa));
    }

    // 2. CARI KAMAR (Berdasarkan nomor kamar unik)
    public Kamar cariKamar(String nomorKamar) {
        if (nomorKamar == null) {
            return null;
        }
        for (Kamar k : daftarKamar) {
            if (k.getNomorKamar().equalsIgnoreCase(nomorKamar.trim())) {
                return k;
            }
        }
        return null;
    }

    // Cari kamar kosong pertama
    public Kamar cariKamarKosong() throws KamarTidakTersediaException {
        for (Kamar k : daftarKamar) {
            if (k.isStatusTersedia()) {
                return k;
            }
        }
        throw new KamarTidakTersediaException("Tidak ada kamar kosong di " + namaKos);
    }

    // 3. TAMPILKAN DAFTAR KAMAR
    public void tampilkanDaftarKamar() {
        System.out.println("=== Daftar Kamar di " + namaKos + " ===");
        if (daftarKamar.isEmpty()) {
            System.out.println("Belum ada kamar terdaftar di " + namaKos + ".");
            return;
        }
        for (Kamar k : daftarKamar) {
            k.tampilkanData();
        }
        System.out.println("Total kamar terdaftar: " + daftarKamar.size());
    }

    // 4. HAPUS KAMAR
    // Aturan bisnis: Kamar yang sedang ditempati penyewa tidak boleh dihapus!
    public boolean hapusKamar(String nomorKamar) {
        if (nomorKamar == null || nomorKamar.isBlank()) {
            throw new IllegalArgumentException("Nomor kamar tidak boleh kosong");
        }
        Kamar k = cariKamar(nomorKamar);
        if (k == null) {
            return false; // kamar tidak ditemukan
        }
        if (!k.isStatusTersedia()) {
            throw new IllegalStateException("Kamar " + nomorKamar + " sedang terisi, tidak dapat dihapus!");
        }
        return daftarKamar.remove(k);
    }

    public int jumlahKamarTersedia() {
        int jumlah = 0;
        for (Kamar k : daftarKamar) {
            if (k.isStatusTersedia()) {
                jumlah++;
            }
        }
        return jumlah;
    }

    public double hitungPotensiPendapatan() {
        double total = 0;
        for (Kamar k : daftarKamar) {
            if (!k.isStatusTersedia()) {
                total += k.getHargaSewa();
            }
        }
        return total;
    }

    // ===========================================================
    // PENGELOLAAN KOLEKSI PENYEWA (Tambah, Cari, Tampilkan, Hapus)
    // ===========================================================

    public int getJumlahPenyewa() {
        return daftarPenyewa.size();
    }

    // 1. TAMBAH / DAFTARKAN PENYEWA
    // Validasi keunikan ID dilakukan SEBELUM kamar ditempati agar state kamar tidak berubah jika ID duplikat
    public Penyewa daftarkanPenyewa(String id, String nama, Kamar kamar, String jatuhTempo)
            throws KamarTidakTersediaException {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID penyewa tidak boleh kosong");
        }
        if (cariPenyewa(id) != null) {
            throw new IllegalArgumentException("ID Penyewa " + id + " sudah terdaftar di " + namaKos);
        }
        if (kamar == null) {
            throw new IllegalArgumentException("Objek kamar tidak boleh null");
        }
        Penyewa p = new Penyewa(id, nama, kamar, jatuhTempo);
        daftarPenyewa.add(p);
        return p;
    }

    public void tambahPenyewa(Penyewa penyewa) {
        if (penyewa == null) {
            throw new IllegalArgumentException("Objek penyewa tidak boleh null");
        }
        if (cariPenyewa(penyewa.getId()) != null) {
            throw new IllegalArgumentException("ID Penyewa " + penyewa.getId()
                    + " sudah terdaftar di " + namaKos);
        }
        daftarPenyewa.add(penyewa);
    }

    // 2. CARI PENYEWA
    public Penyewa cariPenyewa(String id) {
        if (id == null) {
            return null;
        }
        for (Penyewa p : daftarPenyewa) {
            if (p.getId().equalsIgnoreCase(id.trim())) {
                return p;
            }
        }
        return null;
    }

    // 3. TAMPILKAN DAFTAR PENYEWA
    public void tampilkanDaftarPenyewa() {
        System.out.println("=== Daftar Penyewa di " + namaKos + " ===");
        if (daftarPenyewa.isEmpty()) {
            System.out.println("Belum ada penyewa terdaftar di " + namaKos + ".");
            return;
        }
        for (Penyewa p : daftarPenyewa) {
            p.tampilkanData();
        }
        System.out.println("Total penyewa tercatat: " + daftarPenyewa.size());
    }

    // 4. HAPUS PENYEWA
    // Aturan bisnis: Penyewa yang masih aktif menempati kamar tidak boleh dihapus!
    public boolean hapusPenyewa(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID penyewa tidak boleh kosong");
        }
        Penyewa p = cariPenyewa(id);
        if (p == null) {
            return false;
        }
        if (p.isAktif()) {
            throw new IllegalStateException("Penyewa " + p.getNama()
                    + " masih aktif menempati kamar " + p.getKamar().getNomorKamar()
                    + ", selesaikan check-out terlebih dahulu!");
        }
        return daftarPenyewa.remove(p);
    }
}
