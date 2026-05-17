import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    private ArrayList<Menu> menu;
    private ArrayList<Pesanan> pesanan = new ArrayList<Pesanan>();
    private Scanner scanner = new Scanner(System.in);

    public Main(Menu[] menu)
    {
        this.menu = new ArrayList<Menu>(Arrays.asList(menu));
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
        System.out.println("2. Manajemen Menu");
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
            case 2:
                manajemenMenu();
                break;
            default:
                System.out.println("!!!!!!!!!! Pilihan tidak ada !!!!!!!!!!");
                utama();
        }
    }

    public void manajemenMenu()
    {
        System.out.println("---------- MANAJEMEN MENU ----------");

        System.out.println("1. Lihat Menu");
        System.out.println("2. Tambah Menu");
        System.out.println("3. Hapus Menu");
        System.out.println("0. Keluar");

        System.out.print("Pilih Aksi > ");
        int input = 0;

        if(!scanner.hasNextInt()){
            scanner.nextLine();
            System.out.println("!!!!!!!!!! Input Salah !!!!!!!!!!");
            manajemenMenu();
            return;
        }
        input = scanner.nextInt();
        scanner.nextLine();

        switch(input)
        {
            case 0:
                utama();
                break;
            case 1:
                daftarMenu(0);
                manajemenMenu();
                break;
            case 2:
                addMenu();
                break;
            case 3:
                deleteMenu();
                break;
            default:
                System.out.println("!!!!!!!!!! Pilihan tidak ada !!!!!!!!!!");
                manajemenMenu();
        }
    }

    public void addMenu()
    {
        System.out.println("---------- TAMBAH MENU ----------");

        String nama = "";
        double harga = 0;
        String kategori = "";

        System.out.print("Nama Menu > ");
        if(!scanner.hasNextLine()){
            System.out.println("!!!!!!!!!! Input Salah !!!!!!!!!!");
            addMenu();
            return;
        }
        nama = scanner.nextLine();
        if(nama.isEmpty()){
            System.out.println("!!!!!!!!!! Nama menu tidak boleh kosong !!!!!!!!!!");
            addMenu();
        }

        System.out.print("Harga Menu > ");
        if(!scanner.hasNextDouble()){
            System.out.println("!!!!!!!!!! Input Salah !!!!!!!!!!");
            addMenu();
            return;
        }
        harga = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Kategori Menu (Makanan/Minuman) > ");
        if(!scanner.hasNextLine()){
            System.out.println("!!!!!!!!!! Input Salah !!!!!!!!!!");
            addMenu();
            return;
        }
        kategori = scanner.nextLine();
        if(kategori.isEmpty()){
            System.out.println("!!!!!!!!!! Kategori menu tidak boleh kosong !!!!!!!!!!");
            addMenu();
        }else
        if(!kategori.equalsIgnoreCase("Makanan") && !kategori.equalsIgnoreCase("Minuman")){
            System.out.println("!!!!!!!!!! Kategori menu harus Makanan atau Minuman !!!!!!!!!!");
            addMenu();
        }else{
            kategori = kategori.equalsIgnoreCase("Makanan") ? "Makanan" : "Minuman";
        }

        menu.add(new Menu(nama, harga, kategori));
        menu = new ArrayList<Menu>(menu.stream().sorted((a, b) -> {
            if(a.kategori.equals(b.kategori)){
                return a.nama.compareTo(b.nama);
            }else{
                return a.kategori.equals("Makanan") ? -1 : 1;
            }
        }).toList());
        System.out.println("Menu berhasil ditambahkan!");
        manajemenMenu();
    }

    public void deleteMenu()
    {
        System.out.println("---------- HAPUS MENU ----------");

        daftarMenu(0);

        System.out.print("Pilih Menu (0 untuk batal) > ");
        int input = 0;

        if(!scanner.hasNextInt()){
            scanner.nextLine();
            System.out.println("!!!!!!!!!! Input Salah !!!!!!!!!!");
            deleteMenu();
            return;
        }
        input = scanner.nextInt();
        scanner.nextLine();

        if(input == 0){
            manajemenMenu();
        }else
        if(input >= 1 && input <= menu.size()){
            menu.remove(input - 1);
            menu = new ArrayList<Menu>(menu.stream().sorted((a, b) -> {
                if(a.kategori.equals(b.kategori)){
                    return a.nama.compareTo(b.nama);
                }else{
                    return a.kategori.equals("Makanan") ? -1 : 1;
                }
            }).toList());
            System.out.println("Menu berhasil dihapus!");
            manajemenMenu();
        }else{
            System.out.println("!!!!!!!!!! Pilihan tidak ada !!!!!!!!!!");
            deleteMenu();
        }
    }

    public void pemesanan()
    {
        System.out.println("---------- PEMESANAN ----------");

        if(pesanan.size() > 0) daftarPesanan(0);

        daftarMenu(0);

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
        if(input >= 1 && input <= menu.size()){
            addPesanan(menu.get(input - 1));
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
        if(start >= 0 && start < menu.size()){
            if(start == 0 || (start > 0 && menu.get(start - 1).kategori != menu.get(start).kategori)) System.out.println("# " + menu.get(start).kategori);
            System.out.printf("%-2d. %-20s - %,-10.0f\n", start + 1, menu.get(start).nama, menu.get(start).harga);
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