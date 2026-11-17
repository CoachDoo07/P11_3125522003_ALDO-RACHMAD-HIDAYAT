// Custom exception (checked): penyewa mencoba check-out saat tagihan belum lunas.
public class TagihanBelumLunasException extends Exception {
    public TagihanBelumLunasException(String namaPenyewa, String idTagihan) {
        super(namaPenyewa + " tidak bisa check-out, tagihan " + idTagihan + " belum lunas");
    }
}
