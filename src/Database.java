import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private ArrayList<Mahasiswa> data = new ArrayList<>();
    private String filename = "src/data.csv";
    private Path path = Path.of(filename);

    public Database() {
        open();
    }

    public ArrayList<Mahasiswa> getData() {
        return data;
    }

    public void open() {
        try {
            List<String> lines = Files.readAllLines(path);
            data.clear(); // Pastikan data tidak menumpuk saat di-load ulang

            for (int i = 1; i < lines.size(); i++) { // Mulai dari indeks 1, karena indeks 0 adalah header
                String line = lines.get(i).trim();

                if (!line.isEmpty()) { // Pastikan tidak ada baris kosong yang terbaca
                    String[] element = line.split(";");

                    if (element.length == 6) { // Pastikan format data sesuai
                        String nim = element[0];
                        String nama = element[1];
                        String alamat = element[2];
                        int semester = Integer.parseInt(element[3].trim());
                        int sks = Integer.parseInt(element[4].trim());
                        double ipk = Double.parseDouble(element[5].trim());

                        Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
                        data.add(mhs);
                    } else {
                        System.err.println("Format data salah pada baris: " + (i + 1));
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Gagal membaca file: " + e.getMessage());
        }
    }

    public void save() {
        StringBuilder sb = new StringBuilder();
        sb.append("NIM;NAMA;ALAMAT (KOTA);SEMESTER;SKS;IPK\n"); // Sesuaikan header agar tetap rapi

        for (Mahasiswa mhs : data) {
            String line = mhs.getNim() + ";" + mhs.getNama() + ";" + mhs.getAlamat() + ";"
                    + mhs.getSemester() + ";" + mhs.getSks() + ";" + mhs.getIpk() + "\n";
            sb.append(line);
        }

        try {
            Files.writeString(path, sb.toString());
        } catch (IOException e) {
            System.err.println("Gagal menyimpan file: " + e.getMessage());
        }
    }

    public void view() {
        System.out.println("==================================================================================");
        System.out.printf("| %-8.8s | %-20.20s | %-20.20s | %8.8s | %3.3s | %4.4s |\n",
                "NIM", "NAMA", "ALAMAT", "SEMESTER", "SKS", "IPK");
        System.out.println("----------------------------------------------------------------------------------");

        for (Mahasiswa mhs : data) {
            System.out.printf("| %-8s | %-20.20s | %-20.20s | %8d | %3d | %4.2f |\n",
                    mhs.getNim(), mhs.getNama(), mhs.getAlamat(),
                    mhs.getSemester(), mhs.getSks(), mhs.getIpk());
        }
        System.out.println("----------------------------------------------------------------------------------");
    }

    public boolean insert(String nim, String nama, String alamat, int semester, int sks, double ipk) {
        for (Mahasiswa mhs : data) {
            if (mhs.getNim().equalsIgnoreCase(nim)) {
                System.out.println("Gagal: NIM " + nim + " sudah ada di database.");
                return false;
            }
        }

        Mahasiswa newMhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
        data.add(newMhs);
        save();
        return true;
    }

    public int search(String nim) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getNim().equalsIgnoreCase(nim)) {
                return i;
            }
        }
        return -1;
    }

    public boolean update(int index, String nim, String nama, String alamat, int semester, int sks, double ipk) {
        if (index >= 0 && index < data.size()) {
            Mahasiswa mhs = new Mahasiswa(nim, nama, alamat, semester, sks, ipk);
            data.set(index, mhs);
            save();
            return true;
        }
        return false;
    }

    public boolean delete(int index) {
        if (index >= 0 && index < data.size()) {
            data.remove(index);
            save();
            return true;
        }
        return false;
    }
}
