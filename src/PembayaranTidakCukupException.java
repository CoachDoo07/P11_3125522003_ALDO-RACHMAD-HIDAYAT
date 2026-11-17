// Custom exception (checked): jumlah bayar lebih kecil dari nominal tagihan.
public class PembayaranTidakCukupException extends Exception {
    private double kekurangan;

    public PembayaranTidakCukupException(double nominal, double dibayar) {
        super("Pembayaran kurang Rp " + (long) (nominal - dibayar)
                + " (tagihan Rp " + (long) nominal + ", dibayar Rp " + (long) dibayar + ")");
        this.kekurangan = nominal - dibayar;
    }

    public double getKekurangan() {
        return kekurangan;
    }
}
