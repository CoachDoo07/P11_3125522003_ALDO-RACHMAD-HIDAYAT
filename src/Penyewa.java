public class Penyewa extends Pengguna {
    private Kamar kamar;      // Association: Kamar yang ditempati (null setelah check-out)
    private Tagihan tagihan;  // Composition: dibuat sendiri oleh Penyewa

    public Penyewa(String id, String nama, Kamar kamar, String jatuhTempo)
            throws KamarTidakTersediaException {
        super(id, nama); // validasi id & nama ada di Pengguna
        if (kamar == null) {
            throw new IllegalArgumentException("Kamar tidak boleh null");
        }
        // Tagihan dibuat lebih dulu: jika datanya tidak valid, kamar belum disentuh
        Tagihan tagihanBaru = new Tagihan(id.trim() + "-TGH", jatuhTempo, kamar.getHargaSewa());
        // Langkah yang mengubah state kamar ditaruh paling akhir.
        // Jika kamar terisi, exception keluar dan Penyewa tidak pernah jadi.
        kamar.tempati();
        this.kamar = kamar;
        this.tagihan = tagihanBaru;
    }

    public boolean isAktif() {
        return kamar != null;
    }

    public Kamar getKamar() {
        return kamar;
    }

    public Tagihan getTagihan() {
        return tagihan;
    }

    public void pindahKamar(Kamar kamarBaru) throws KamarTidakTersediaException {
        if (!isAktif()) {
            throw new IllegalStateException(getNama() + " tidak aktif, tidak bisa pindah kamar");
        }
        if (kamarBaru == null || kamarBaru == kamar) {
            throw new IllegalArgumentException("Kamar tujuan tidak valid");
        }
        kamarBaru.tempati();  // jika gagal, kamar lama belum disentuh
        kamar.kosongkan();
        kamar = kamarBaru;
    }

    // Business rule: check-out hanya boleh jika tagihan sudah lunas
    public void checkOut() throws TagihanBelumLunasException {
        if (!isAktif()) {
            throw new IllegalStateException(getNama() + " sudah tidak menempati kamar");
        }
        if (!tagihan.cekStatus()) {
            throw new TagihanBelumLunasException(getNama(), tagihan.getIdTagihan());
        }
        kamar.kosongkan();
        kamar = null;
    }

    @Override
    public void tampilkanPeran() {
        System.out.println("Penyewa - menempati kamar dan membayar tagihan sewa");
    }

    public void tampilkanData() {
        tampilkanInfo();
        System.out.println("Status Aktif: " + (isAktif() ? "Aktif (Menempati Kamar " + kamar.getNomorKamar() + ")" : "Nonaktif (Sudah Check-out)"));
        System.out.println("Status Tagihan: " + (tagihan.cekStatus() ? "Lunas" : "Belum Lunas"));
        System.out.println("--------------------");
    }
}
