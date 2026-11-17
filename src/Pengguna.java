public abstract class Pengguna {
    private String id;
    private String nama;

    public Pengguna(String id, String nama) {
        // Validasi dilakukan SEBELUM state diisi
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID pengguna tidak boleh kosong");
        }
        if (nama == null || nama.isBlank()) {
            throw new IllegalArgumentException("Nama pengguna tidak boleh kosong");
        }
        this.id = id.trim();
        this.nama = nama.trim();
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void tampilkanInfo() {
        System.out.println("ID   : " + id);
        System.out.println("Nama : " + nama);
    }

    // Abstract method: setiap jenis pengguna punya peran yang berbeda
    public abstract void tampilkanPeran();
}
