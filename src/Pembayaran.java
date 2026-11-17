public class Pembayaran {
    private String idPembayaran;
    private String tanggalBayar;
    private double jumlahBayar;
    private Penyewa penyewa; // Association: pembayaran dilakukan oleh penyewa

    public Pembayaran(String idPembayaran, String tanggalBayar, double jumlahBayar, Penyewa penyewa) {
        if (idPembayaran == null || idPembayaran.isBlank()) {
            throw new IllegalArgumentException("ID pembayaran tidak boleh kosong");
        }
        if (tanggalBayar == null || tanggalBayar.isBlank()) {
            throw new IllegalArgumentException("Tanggal bayar tidak boleh kosong");
        }
        if (penyewa == null) {
            throw new IllegalArgumentException("Penyewa tidak boleh null");
        }
        if (jumlahBayar <= 0) {
            throw new IllegalArgumentException("Jumlah bayar harus lebih dari 0, bukan: " + (long) jumlahBayar);
        }
        this.idPembayaran = idPembayaran.trim();
        this.tanggalBayar = tanggalBayar.trim();
        this.jumlahBayar = jumlahBayar;
        this.penyewa = penyewa;
    }

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public String getTanggalBayar() {
        return tanggalBayar;
    }

    public double getJumlahBayar() {
        return jumlahBayar;
    }

    public Penyewa getPenyewa() {
        return penyewa;
    }

    public void prosesPembayaran() throws PembayaranTidakCukupException {
        if (!penyewa.isAktif()) {
            throw new IllegalStateException("Penyewa " + penyewa.getNama() + " tidak aktif, pembayaran ditolak");
        }
        penyewa.getTagihan().bayar(jumlahBayar); // exception dari Tagihan diteruskan ke pemanggil
    }

    public void tampilkanData() {
        System.out.println("ID Pembayaran  : " + idPembayaran);
        System.out.println("Penyewa        : " + penyewa.getNama());
        System.out.println("Tanggal Bayar  : " + tanggalBayar);
        System.out.println("Jumlah Bayar   : Rp " + (long) jumlahBayar);
        System.out.println("-----------------------");
    }
}
