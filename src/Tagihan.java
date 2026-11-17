public class Tagihan implements DapatDicekStatus {
    private String idTagihan;
    private String jatuhTempo;
    private double nominal;
    private boolean statusLunas;

    public Tagihan(String idTagihan, String jatuhTempo, double nominal) {
        if (idTagihan == null || idTagihan.isBlank()) {
            throw new IllegalArgumentException("ID tagihan tidak boleh kosong");
        }
        if (jatuhTempo == null || jatuhTempo.isBlank()) {
            throw new IllegalArgumentException("Jatuh tempo tidak boleh kosong");
        }
        if (nominal <= 0) {
            throw new IllegalArgumentException("Nominal tagihan harus lebih dari 0");
        }
        this.idTagihan = idTagihan.trim();
        this.jatuhTempo = jatuhTempo.trim();
        this.nominal = nominal;
        this.statusLunas = false;
    }

    public String getIdTagihan() {
        return idTagihan;
    }

    public String getJatuhTempo() {
        return jatuhTempo;
    }

    public double getNominal() {
        return nominal;
    }

    // Seluruh pemeriksaan dilakukan dulu; statusLunas baru berubah di baris terakhir
    public void bayar(double jumlah) throws PembayaranTidakCukupException {
        if (statusLunas) {
            throw new IllegalStateException("Tagihan " + idTagihan + " sudah lunas");
        }
        if (jumlah <= 0) {
            throw new IllegalArgumentException("Jumlah bayar harus lebih dari 0");
        }
        if (jumlah < nominal) {
            throw new PembayaranTidakCukupException(nominal, jumlah);
        }
        statusLunas = true;
    }

    @Override
    public boolean cekStatus() {
        return statusLunas;
    }

    public void tampilkanData() {
        System.out.println("ID Tagihan  : " + idTagihan);
        System.out.println("Jatuh Tempo : " + jatuhTempo);
        System.out.println("Nominal     : Rp " + (long) nominal);
        System.out.println("Status      : " + (statusLunas ? "Lunas" : "Belum Lunas"));
        System.out.println("--------------------");
    }
}
