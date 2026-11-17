public class Main {

    // ---------- Helper Tampilan State ----------
    static String formatKamar(Kamar k) {
        if (k == null) return "null";
        return k.getNomorKamar() + " (" + k.getTipeKamar() + ", "
                + (k.isStatusTersedia() ? "Tersedia" : "Terisi") + ")";
    }

    public static void main(String[] args) {
        System.out.println("========================================================================");
        System.out.println("   SIKOS (SISTEM INFORMASI MANAJEMEN KOS) - TUGAS PRAKTIKUM P11        ");
        System.out.println("   Pengelolaan Kumpulan Objek dengan ArrayList, Generics, & Exception  ");
        System.out.println("   Nama: Aldo Rachmad Hidayat | NRP: 3125522003                        ");
        System.out.println("========================================================================\n");

        Kos kos = new Kos("Kos Sejahtera");
        PemilikKos pemilik = new PemilikKos("PK01", "Haidar", "0812-3456-7890", kos);

        // ====================================================================
        // BAGIAN 1: OPERASI TAMBAH DAN CARI PADA KOLEKSI KAMAR
        // ====================================================================
        System.out.println(">>> 1. OPERASI KOLEKSI KAMAR (TAMBAH & CARI)");

        // [T1] Tambah Kamar K01 dan K02 (Berhasil)
        System.out.println("\n[T1] Tambah Kamar K01 (Single, 500000) dan K02 (Double, 750000)");
        try {
            kos.tambahKamar("K01", "Single", 500000);
            kos.tambahKamar(new Kamar("K02", "Double", 750000));
            System.out.println("  Hasil : BERHASIL - Kamar K01 dan K02 berhasil ditambahkan ke koleksi.");
        } catch (IllegalArgumentException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Total kamar di Kos = " + kos.getJumlahKamar());
        }

        // [T2] Tambah Kamar K01 duplikat (Ditolak)
        System.out.println("\n[T2] Tambah Kamar dengan kode duplikat K01");
        try {
            kos.tambahKamar("K01", "Single", 500000);
            System.out.println("  Hasil : BERHASIL");
        } catch (IllegalArgumentException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Total kamar di Kos = " + kos.getJumlahKamar());
        }

        // [T3] Tambah Kamar dengan harga sewa tidak valid (<= 0)
        System.out.println("\n[T3] Tambah Kamar K04 dengan harga sewa tidak valid (-100000)");
        try {
            kos.tambahKamar("K04", "Single", -100000);
            System.out.println("  Hasil : BERHASIL");
        } catch (IllegalArgumentException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Total kamar di Kos = " + kos.getJumlahKamar());
        }

        // [T4] Cari Kamar K02 yang terdaftar (Berhasil)
        System.out.println("\n[T4] Cari Kamar K02 di dalam koleksi");
        Kamar k02 = kos.cariKamar("K02");
        if (k02 != null) {
            System.out.println("  Hasil : DITEMUKAN - " + formatKamar(k02) + " seharga Rp " + (long) k02.getHargaSewa());
        } else {
            System.out.println("  Hasil : Kamar tidak ditemukan");
        }
        System.out.println("  State : Total kamar di Kos = " + kos.getJumlahKamar());

        // [T5] Cari Kamar K99 yang tidak terdaftar
        System.out.println("\n[T5] Cari Kamar K99 (tidak terdaftar)");
        Kamar k99 = kos.cariKamar("K99");
        System.out.println("  Hasil : " + (k99 != null ? "Ditemukan" : "TIDAK DITEMUKAN (null)"));
        System.out.println("  State : Total kamar di Kos = " + kos.getJumlahKamar());

        // Tambah K03 untuk cadangan pengujian
        kos.tambahKamar("K03", "Single", 500000);
        System.out.println("\n(Info: Kamar K03 ditambahkan. Total kamar sekarang: " + kos.getJumlahKamar() + ")");

        // ====================================================================
        // BAGIAN 2: OPERASI KOLEKSI PENYEWA & PENDAFTARAN
        // ====================================================================
        System.out.println("\n\n>>> 2. OPERASI KOLEKSI PENYEWA & PENDAFTARAN");

        Kamar k01 = kos.cariKamar("K01");
        Penyewa budi = null;

        // [T6] Daftarkan Penyewa Budi (P01) menempati K01
        System.out.println("\n[T6] Daftarkan Penyewa Budi (P01) menempati K01");
        try {
            budi = kos.daftarkanPenyewa("P01", "Budi", k01, "2026-11-30");
            System.out.println("  Hasil : BERHASIL - " + budi.getNama() + " terdaftar dan menempati kamar " + k01.getNomorKamar());
        } catch (KamarTidakTersediaException | IllegalArgumentException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Total penyewa tercatat = " + kos.getJumlahPenyewa()
                    + " | Status K01 = " + (k01.isStatusTersedia() ? "Tersedia" : "Terisi"));
        }

        // [T7] Tambah Penyewa dengan ID duplikat (P01)
        System.out.println("\n[T7] Tambah Penyewa dengan ID duplikat P01");
        Kamar k03 = kos.cariKamar("K03");
        try {
            kos.daftarkanPenyewa("P01", "Siti", k03, "2026-11-30");
            System.out.println("  Hasil : BERHASIL");
        } catch (IllegalArgumentException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } catch (KamarTidakTersediaException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Total penyewa tercatat = " + kos.getJumlahPenyewa()
                    + " | Status K03 = " + (k03.isStatusTersedia() ? "Tersedia" : "Terisi"));
        }

        // [T8] Cari Penyewa P01 yang terdaftar
        System.out.println("\n[T8] Cari Penyewa dengan ID P01");
        Penyewa hasilCariP01 = kos.cariPenyewa("P01");
        if (hasilCariP01 != null) {
            System.out.println("  Hasil : DITEMUKAN - " + hasilCariP01.getNama()
                    + " (Menempati: " + hasilCariP01.getKamar().getNomorKamar() + ")");
        } else {
            System.out.println("  Hasil : Penyewa tidak ditemukan");
        }
        System.out.println("  State : Total penyewa tercatat = " + kos.getJumlahPenyewa());

        // ====================================================================
        // BAGIAN 3: VALIDASI PENGHAPUSAN KAMAR (ATURAN BISNIS)
        // ====================================================================
        System.out.println("\n\n>>> 3. VALIDASI OPERASI PENGHAPUSAN KAMAR");

        // [T9] Hapus Kamar K01 yang sedang terisi (Aturan Bisnis: DITOLAK)
        System.out.println("\n[T9] Coba hapus Kamar K01 (saat ini sedang ditempati oleh Budi)");
        try {
            boolean terhapus = kos.hapusKamar("K01");
            System.out.println("  Hasil : " + (terhapus ? "BERHASIL DIHAPUS" : "GAGAL"));
        } catch (IllegalStateException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Kamar K01 tetap ada | Total kamar di Kos = " + kos.getJumlahKamar());
        }

        // [T10] Hapus Kamar K02 yang kosong/tersedia (Berhasil)
        System.out.println("\n[T10] Hapus Kamar K02 (status: kosong / tersedia)");
        try {
            boolean terhapus = kos.hapusKamar("K02");
            System.out.println("  Hasil : BERHASIL - Kamar K02 dihapus dari sistem (return " + terhapus + ")");
        } catch (IllegalStateException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Total kamar di Kos = " + kos.getJumlahKamar());
        }

        // [T11] Hapus Kamar K99 yang tidak terdaftar
        System.out.println("\n[T11] Hapus Kamar K99 (tidak terdaftar)");
        boolean statusHapusK99 = kos.hapusKamar("K99");
        System.out.println("  Hasil : Kamar tidak ditemukan (return " + statusHapusK99 + ")");
        System.out.println("  State : Total kamar di Kos = " + kos.getJumlahKamar());

        // ====================================================================
        // BAGIAN 4: VALIDASI PENGHAPUSAN PENYEWA, PEMBAYARAN & CHECK-OUT
        // ====================================================================
        System.out.println("\n\n>>> 4. VALIDASI PENGHAPUSAN PENYEWA & ALUR CHECK-OUT");

        // [T12] Coba hapus Penyewa P01 yang masih aktif menempati kamar (Ditolak)
        System.out.println("\n[T12] Coba hapus Penyewa P01 (masih aktif menempati kamar)");
        try {
            boolean terhapus = kos.hapusPenyewa("P01");
            System.out.println("  Hasil : " + (terhapus ? "BERHASIL" : "GAGAL"));
        } catch (IllegalStateException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Penyewa P01 tetap ada | Total penyewa = " + kos.getJumlahPenyewa());
        }

        // [T13] Pembayaran Tagihan Budi (Kurang vs Tepat)
        System.out.println("\n[T13] Pembayaran Tagihan Budi");
        try {
            System.out.println("  - Percobaan bayar Rp 300.000 (tagihan Rp 500.000):");
            Pembayaran bayarKurang = new Pembayaran("BY01", "2026-11-17", 300000, budi);
            bayarKurang.prosesPembayaran();
        } catch (PembayaranTidakCukupException e) {
            System.out.println("    Hasil : DITOLAK - " + e.getMessage());
        }

        try {
            System.out.println("  - Pembayaran pelunasan Rp 500.000:");
            Pembayaran bayarLunas = new Pembayaran("BY02", "2026-11-17", 500000, budi);
            bayarLunas.prosesPembayaran();
            System.out.println("    Hasil : BERHASIL - Tagihan Budi lunas!");
        } catch (PembayaranTidakCukupException e) {
            System.out.println("    Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Status tagihan Budi = " + (budi.getTagihan().cekStatus() ? "Lunas" : "Belum Lunas"));
        }

        // [T14] Check-out Budi dari Kamar K01
        System.out.println("\n[T14] Budi melakukan check-out");
        try {
            budi.checkOut();
            System.out.println("  Hasil : BERHASIL - Budi check-out, kamar K01 telah dikosongkan");
        } catch (TagihanBelumLunasException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Budi aktif = " + budi.isAktif() + " | Status K01 = "
                    + (k01.isStatusTersedia() ? "Tersedia" : "Terisi"));
        }

        // [T15] Hapus Penyewa Budi setelah check-out (Berhasil)
        System.out.println("\n[T15] Hapus Penyewa Budi dari koleksi (setelah status nonaktif/check-out)");
        try {
            boolean terhapus = kos.hapusPenyewa("P01");
            System.out.println("  Hasil : BERHASIL - Penyewa P01 dihapus dari riwayat aktif (return " + terhapus + ")");
        } catch (IllegalStateException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Total penyewa tercatat = " + kos.getJumlahPenyewa());
        }

        // [T16] Hapus Kamar K01 yang kini sudah kosong
        System.out.println("\n[T16] Hapus Kamar K01 (sekarang sudah kosong pasca check-out)");
        try {
            boolean terhapus = kos.hapusKamar("K01");
            System.out.println("  Hasil : BERHASIL - Kamar K01 dihapus dari koleksi (return " + terhapus + ")");
        } catch (IllegalStateException e) {
            System.out.println("  Hasil : DITOLAK - " + e.getMessage());
        } finally {
            System.out.println("  State : Total kamar di Kos = " + kos.getJumlahKamar());
        }

        // Hapus kamar K03 yang masih tersedia
        System.out.println("\n(Info: Menghapus kamar K03 yang tersedia...)");
        kos.hapusKamar("K03");
        System.out.println("  State : Total kamar di Kos = " + kos.getJumlahKamar());

        // [T17] Tampilkan koleksi saat kosong
        System.out.println("\n[T17] Tampilkan daftar kamar & penyewa saat koleksi kosong");
        kos.tampilkanDaftarKamar();
        kos.tampilkanDaftarPenyewa();
        System.out.println("  State : Total kamar = " + kos.getJumlahKamar() + ", Total penyewa = " + kos.getJumlahPenyewa());

        // ====================================================================
        // BAGIAN 5: LAPORAN AKHIR PEMILIK KOS
        // ====================================================================
        System.out.println("\n\n>>> 5. REKAPITULASI LAPORAN PEMILIK KOS");
        pemilik.tampilkanLaporanKos();
    }
}
