public class PemilikKos extends Pengguna {
    private String nomorTelepon;
    private Kos kos; // Association: pemilik mengelola satu Kos

    public PemilikKos(String id, String nama, String nomorTelepon, Kos kos) {
        super(id, nama);
        if (kos == null) {
            throw new IllegalArgumentException("Kos tidak boleh null");
        }
        cekTelepon(nomorTelepon);
        this.nomorTelepon = nomorTelepon.trim();
        this.kos = kos;
    }

    private static void cekTelepon(String nomor) {
        if (nomor == null || nomor.isBlank()) {
            throw new IllegalArgumentException("Nomor telepon tidak boleh kosong");
        }
    }

    public String getNomorTelepon() {
        return nomorTelepon;
    }

    public Kos getKos() {
        return kos;
    }

    public void ubahNomorTelepon(String nomorBaru) {
        cekTelepon(nomorBaru);
        this.nomorTelepon = nomorBaru.trim();
    }

    public void tampilkanLaporanKos() {
        System.out.println("=== Laporan " + kos.getNamaKos() + " ===");
        System.out.println("Pemilik Kos         : " + getNama() + " (" + nomorTelepon + ")");
        System.out.println("Kamar terdaftar     : " + kos.getJumlahKamar());
        System.out.println("Kamar tersedia      : " + kos.jumlahKamarTersedia());
        System.out.println("Penyewa terdaftar   : " + kos.getJumlahPenyewa());
        System.out.println("Potensi pendapatan  : Rp " + (long) kos.hitungPotensiPendapatan() + " / bulan");
        System.out.println("-------------------------");
    }

    @Override
    public void tampilkanPeran() {
        System.out.println("Pemilik Kos - mengelola data kamar dan memantau penyewa");
    }
}
