import java.util.Scanner;

public class UserInterFace {

    public static void tampilkanMenu() {
        System.out.println();
        System.out.println("+================+");
        System.out.println("| Pilih menu:   |");
        System.out.println("+----------------+");
        System.out.println("| [C] : Create  |");
        System.out.println("| [R] : Read    |");
        System.out.println("| [U] : Update  |");
        System.out.println("| [D] : Delete  |");
        System.out.println("| [X] : Exit    |");
        System.out.println("+================+");
    }

    public static void main(String[] args) {
        Database db = new Database();
        Scanner sc = new Scanner(System.in);  // Scanner dibuat satu kali

        System.out.println("APLIKASI SIMPLE CRUD TEXT DATABASE");
        while (true) {
            tampilkanMenu();
            System.out.print("Pilih : ");
            String pilihan = sc.nextLine().trim().toUpperCase();

            switch (pilihan) {
                case "C":
                    System.out.println("INFO: Anda memilih menu Create");
                    System.out.println("--------------------------------");
                    System.out.println("INPUT DATA BARU");
                    System.out.print("NIM             : ");
                    String nim = sc.nextLine();
                    System.out.print("NAMA MAHASISWA  : ");
                    String nama = sc.nextLine();
                    System.out.print("ALAMAT          : ");
                    String alamat = sc.nextLine();
                    System.out.print("SEMESTER        : ");
                    int semester = sc.nextInt();
                    System.out.print("SKS             : ");
                    int sks = sc.nextInt();
                    System.out.print("IPK             : ");
                    double ipk = sc.nextDouble();
                    sc.nextLine(); // Membersihkan buffer

                    boolean status = db.insert(nim, nama, alamat, semester, sks, ipk);
                    if (status) {
                        System.out.println("DATA BARU BERHASIL DITAMBAHKAN");
                    } else {
                        System.out.println("NIM " + nim + " sudah ada di database");
                        System.out.println("GAGAL MENAMBAHKAN DATA BARU");
                    }
                    break;

                case "R":
                    System.out.println("INFO: Anda memilih menu Read");
                    db.view();
                    break;

                case "U":
                    System.out.println("INFO: Anda memilih menu Update");
                    db.view();
                    System.out.print("Input Key (NIM Mahasiswa yang akan diupdate): ");
                    String key = sc.nextLine();
                    int index = db.search(key);

                    if (index >= 0) {
                        Mahasiswa mhs = db.getData().get(index);
                        System.out.println("Anda akan meng-update data:");
                        System.out.println("NIM: " + mhs.getNim() + ", Nama: " + mhs.getNama() + ", Alamat: " + mhs.getAlamat());
                        System.out.println("--------------------------------");
                        System.out.println("INPUT DATA BARU");

                        System.out.print("NIM             : ");
                        nim = sc.nextLine();
                        System.out.print("NAMA MAHASISWA  : ");
                        nama = sc.nextLine();
                        System.out.print("ALAMAT          : ");
                        alamat = sc.nextLine();
                        System.out.print("SEMESTER        : ");
                        semester = sc.nextInt();
                        System.out.print("SKS             : ");
                        sks = sc.nextInt();
                        System.out.print("IPK             : ");
                        ipk = sc.nextDouble();
                        sc.nextLine(); // Membersihkan buffer

                        db.update(index, nim, nama, alamat, semester, sks, ipk);
                        System.out.println("DATA BERHASIL DIUPDATE");
                    } else {
                        System.err.println("Mahasiswa dengan NIM: " + key + " tidak ada di database");
                    }
                    break;

                case "D":
                    System.out.println("INFO: Anda memilih menu Delete");
                    db.view();
                    System.out.print("Input Key (NIM Mahasiswa yang akan dihapus): ");
                    key = sc.nextLine();
                    index = db.search(key);

                    if (index >= 0) {
                        Mahasiswa mhs = db.getData().get(index);
                        System.out.println("Anda akan menghapus data:");
                        System.out.println("NIM: " + mhs.getNim() + ", Nama: " + mhs.getNama());
                        System.out.print("Apakah Anda yakin ingin menghapus data ini? (Y/N): ");
                        pilihan = sc.nextLine().trim().toUpperCase();

                        if (pilihan.equals("Y")) {
                            status = db.delete(index);
                            if (status) {
                                System.out.println("DATA BERHASIL DIHAPUS");
                            } else {
                                System.out.println("GAGAL MENGHAPUS DATA");
                            }
                        }
                    } else {
                        System.err.println("Mahasiswa dengan NIM: " + key + " tidak ada di database");
                    }
                    break;

                case "X":
                    System.out.println("INFO: Anda memilih menu EXIT");
                    System.out.print("APAKAH ANDA YAKIN AKAN KELUAR DARI APLIKASI? (Y/N): ");
                    pilihan = sc.nextLine().trim().toUpperCase();

                    if (pilihan.equals("Y")) {
                        System.out.println("Aplikasi ditutup.");
                        sc.close();  // Menutup Scanner sebelum keluar
                        System.exit(0);
                    }
                    break;

                default:
                    System.out.println("Pilihan tidak valid! Silakan pilih menu yang tersedia.");
            }
        }
    }
}
