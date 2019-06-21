package web.id.azammukhtar.peka;

import java.util.ArrayList;
import java.util.List;

import web.id.azammukhtar.peka.Model.Pertanyaan;

public class DummyData {
    public static List<Pertanyaan> createKategoriList() {
        List<Pertanyaan> pertanyaan = new ArrayList<>();
        pertanyaan.add(new Pertanyaan(1, "Siapakah penemu bola lampu?",
                "Thomas Alfa Edison",
                "Thomas Alfamart",
                "Renayldo",
                "Renayldo Kakap",
                "Thomaes Alfa Edison",
                "Pengetahuan Umum",
                "SD Kelas 1"
                ));
        pertanyaan.add(new Pertanyaan(
                1,
                "Dengan apa ikan hiu bernapas melalui?",
                "Insang",
                "Paru-paru",
                "Perut",
                "Sendirinya",
                "Insang",
                "Pengetahuan Alam",
                "SD Kelas 1"
        ));
        pertanyaan.add(new Pertanyaan(1, "5 + 5 = ?",
                "10",
                "17",
                "29",
                "12",
                "10",
                "Matematika",
                "SD Kelas 2"
        ));
        return pertanyaan;
    }
}
