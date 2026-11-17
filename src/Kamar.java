public class Kamar implements DapatDicekStatus {
    private String nomorKamar;
    private String tipeKamar;
    private double hargaSewa;
    private boolean statusTersedia;

    public Kamar(String nomorKamar, String tipeKamar, double hargaSewa) {
        if (nomorKamar == null || nomorKamar.isBlank()) {
            throw new IllegalArgumentException("Nomor kamar tidak boleh kosong");
        }
        cekTipe(tipeKamar);
        cekHarga(hargaSewa);
        this.nomorKamar = nomorKamar.trim();
        this.tipeKamar = tipeKamar.trim();
        this.hargaSewa = hargaSewa;
        this.statusTersedia = true; // kamar baru selalu tersedia
    }

    private static void cekTipe(String tipe) {
        if (tipe == null || !(tipe.trim().equalsIgnoreCase("Single") || tipe.trim().equalsIgnoreCase("Double"))) {
            throw new IllegalArgumentException("Tipe kamar harus Single atau Double, bukan: " + tipe);
        }
    }

    private static void cekHarga(double harga) {
        if (harga <= 0) {
            throw new IllegalArgumentException("Harga sewa harus lebih dari 0, bukan: " + (long) harga);
        }
    }

    public String getNomorKamar() {
        return nomorKamar;
    }

    public String getTipeKamar() {
        return tipeKamar;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public boolean isStatusTersedia() {
        return statusTersedia;
    }

    public void ubahTipe(String tipeBaru) {
        cekTipe(tipeBaru); // periksa dulu, baru ubah state
        this.tipeKamar = tipeBaru.trim();
    }

    public void ubahHargaSewa(double hargaBaru) {
        cekHarga(hargaBaru);
        this.hargaSewa = hargaBaru;
    }

    public void tempati() throws KamarTidakTersediaException {
        if (!statusTersedia) {
            throw new KamarTidakTersediaException("Kamar " + nomorKamar + " sudah terisi");
        }
        statusTersedia = false;
    }

    public void kosongkan() {
        statusTersedia = true;
    }

    @Override
    public boolean cekStatus() {
        return statusTersedia;
    }

    public void tampilkanData() {
        System.out.println("Nomor Kamar : " + nomorKamar);
        System.out.println("Tipe Kamar  : " + tipeKamar);
        System.out.println("Harga Sewa  : Rp " + (long) hargaSewa);
        System.out.println("Status      : " + (statusTersedia ? "Tersedia" : "Terisi"));
        System.out.println("------------------");
    }
}
