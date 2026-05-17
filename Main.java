import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    private Menu[] menu;
    private ArrayList<Pesanan> pesanan = new ArrayList<Pesanan>();
    private Scanner scanner = new Scanner(System.in);

    public Main(Menu[] menu)
    {
        this.menu = menu;
    }

    public static void main(String[] args)
    {
        Main main = new Main(new Menu[]{
            new Menu("Mie Ayam", 13000, "Makanan"),
            new Menu("Baso", 15000, "Makanan"),
            new Menu("Nasi Goreng", 10000, "Makanan"),
            new Menu("Sate", 20000, "Makanan"),
            new Menu("Es Teh", 2500, "Minuman"),
            new Menu("Kopi", 5000, "Minuman"),
            new Menu("Es Kelapa Muda", 7000, "Minuman"),
            new Menu("Air Mineral", 1000, "Minuman"),
        });

        main.utama();
    }

    public void utama()
    {
        System.out.println("---------- UTAMA ----------");

        pesanan.clear();

        System.out.println("1. Pesan");
        System.out.println("0. Keluar");

        System.out.print("Pilih Aksi > ");
        int input = 0;

        if(!scanner.hasNextInt()){
            scanner.nextLine();
            System.out.println("!!!!!!!!!! Input Salah !!!!!!!!!!");
            utama();
            return;
        }
        input = scanner.nextInt();
        scanner.nextLine();

        switch(input)
        {
            case 0:
                scanner.close();
                System.exit(0);
                break;
            case 1:
                pemesanan();
                break;
            default:
                System.out.println("!!!!!!!!!! Pilihan tidak ada !!!!!!!!!!");
                utama();
        }
    }

    public void pemesanan()
    {
        System.out.println("---------- PEMESANAN ----------");

        if(pesanan.size() > 0) daftarPesanan(0);

        if(pesanan.size() < 4) daftarMenu(0);

        if(pesanan.size() > 0) System.out.println("99. Bayar");

        System.out.print("Pilih Menu (0 untuk batal) > ");
        int input = 0;

        if(!scanner.hasNextInt()){
            scanner.nextLine();
            System.out.println("!!!!!!!!!! Input Salah !!!!!!!!!!");
            pemesanan();
            return;
        }
        input = scanner.nextInt();
        scanner.nextLine();

        if(input == 0){
            utama();
        }else
        if(pesanan.size() > 0 && input == 99){
            bayar();
        }else
        if(pesanan.size() < 4 && input >= 1 && input <= menu.length){
            addPesanan(menu[input - 1]);
        }else{
            System.out.println("!!!!!!!!!! Pilihan tidak ada !!!!!!!!!!");
            pemesanan();
        }
    }

    public void addPesanan(Menu menu)
    {
        System.out.print(menu.nama + " (0 untuk batal) > ");
        int input = 0;

        if(!scanner.hasNextInt()){
            scanner.nextLine();
            System.out.println("!!!!!!!!!! Input Salah !!!!!!!!!!");
            addPesanan(menu);
            return;
        }
        input = scanner.nextInt();
        scanner.nextLine();

        if(input == 0){
            pemesanan();
        }else
        if(input > 0){
            pesanan.add(new Pesanan(menu, input));
            pemesanan();
        }else{
            System.out.println("!!!!!!!!!! Jumlah minimal 1 !!!!!!!!!!");
            addPesanan(menu);
        }
    }

    public void bayar()
    {
        System.out.println("---------- BAYAR ----------");

        cetak(null);

        System.out.print("Masukan nominal (0 untuk batal) > ");
        int input = 0;

        if(!scanner.hasNextInt()){
            scanner.nextLine();
            System.out.println("!!!!!!!!!! Input Salah !!!!!!!!!!");
            bayar();
            return;
        }
        input = scanner.nextInt();
        scanner.nextLine();

        if(input == 0){
            pemesanan();
        }else{
            cetak(Integer.valueOf(input));
        }
    }

    public void cetak(Integer nominal)
    {
        if(nominal != null) System.out.println("---------- STRUK ----------");

        double subTotal = daftarPesanan(0);
        double pajak = subTotal * 0.1;
        double biayaPelayanan = 20_000;
        double diskon = subTotal > 100_000 ? subTotal * 0.1 : 0;
        double total = subTotal + pajak + biayaPelayanan - diskon;
        double kembalian = nominal != null ? Double.valueOf(nominal) - total : 0;

        System.out.println("\n## RINCIAN ##");
        System.out.printf("%-20s : %,-10.0f\n", "SUB TOTAL", subTotal);
        System.out.printf("%-20s : %,-10.0f\n", "PAJAK 10%", pajak);
        System.out.printf("%-20s : %,-10.0f\n", "BIAYA PELAYANAN", biayaPelayanan);
        if(subTotal > 100_000) System.out.printf("%-20s : %,-10.0f\n", "DISKON 10%", diskon);
        System.out.printf("%-20s : %,-10.0f\n", "TOTAL", total);
        if(nominal != null) System.out.printf("%-20s : %,-10d\n", "NOMINAL BAYAR", nominal);
        if(nominal != null) System.out.printf("%-20s : %,-10.0f\n", "KEMBALIAN", kembalian);
    }

    public void daftarMenu(int start)
    {
        if(start == 0) System.out.println("\n## DAFTAR MENU ##");
        if(start >= 0 && start < menu.length){
            if(start == 0 || (start > 0 && menu[start - 1].kategori != menu[start].kategori)) System.out.println("# " + menu[start].kategori);
            System.out.printf("%-2d. %-20s - %,-10.0f\n", start + 1, menu[start].nama, menu[start].harga);
            daftarMenu(start + 1);
        }
    }

    public double daftarPesanan(int start)
    {
        double total = 0;
        if(start == 0 && pesanan.size() > 0) System.out.println("\n## DAFTAR PESANAN ##");
        if(start >= 0 && start < pesanan.size()){
            total = pesanan.get(start).jumlah * pesanan.get(start).menu.harga;
            System.out.printf("%-20s %-2d x %,-15.0f = %,-15.0f\n", pesanan.get(start).menu.nama, pesanan.get(start).jumlah, pesanan.get(start).menu.harga, total);
            total += daftarPesanan(start + 1);
        }
        return total;
    }
}