package fan.company.springbootjwtrealprojectuserindb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJwtRealProjectUserInDbApplication {

    /**
     *                                                Tizim haqida
     * Ushbu tizim HR management uchun hizmat qiluvchi platforma hisoblanadi.
     * Tizimga username va parol orqali kiriladi.
     * Kompanya
     * Kompaniyaning bitta direktori bo’ladi.
     * Direktor managerlarni qo’shadi.
     * Managerlarni qo’shganda ularning email manziliga link jo’natiladi.
     * Bo’lim rahbarlari ichida HR_MANAGER degan lavozim bo’lishi kerak.
     * Ushbu lavozimdagi manager tizimga boshqa xodimlarni qo’shadi. Xodimlar ro’yxatga olinganda ularning ham email manziliga link jo’natiladi.
     *
     *                                  Xodimlar
     * Xodimlar tizimga kirish uchun emailiga kelgan link orqali tizimda o’ziga parol qo’yadi.
     * Xodimlarni email manzili takrorlanmas username hisobalandi.
     * Xodimlarga turniket beriladi.
     * Vazifa
     * Vazifa uch xil holatda bo’ladi(yangi, jarayonda, bajarilgan)
     * Direktor managerlarga va xodimlarga, managerlar esa faqat xodimlarga vazifa biriktira oladi.
     * Har bir vazifa uchun vazifa nomi, vazifa  haqida izoh, vazifaning yakunlanishi kerak bo’lgan vaqti kiritiladi va ushbu vazifaga xodim biriktiriladi.
     * Vazifa biriktirilgan xodimning email manziliga xabar jo’natiladi.
     * Shu bilan birga xodimlarning tizimdagi oynasida ushbu vazifalar ko’rinishi kerak.
     * Xodim vazfani bajarib bo’lgach bajarilganligi haqida belgi qo’yadi va ushbu vazifa bajarilganligi haqida vazifa qoygan manager yoki direktorning emailiga xabar boradi.
     *
     *
     *
     * Turni-ket
     * Xodimlar ish vaqtini belgilash uchun korxonaga turniket qo’yiladi.
     * Turniketning kirish va chiqish tomoni bo’ladi.
     * Turniketning qaysi tomonidan scanner qilinganiga korxonaga kirgan yoki chiqqan vaqt haqida malumotlar qayd etib borilishi kerak.
     *
     * Rahbaniyat
     * Dirktor va HR_MANAGER lavozimidagi manager uchun xodimlar ro’yxati ko’rinib turadi.
     * Har bir xodim haqidagi ma’lumotlarni ko’rmochi bo’lsa ushbu xodimning belgilangan oraliq vaqt bo’yicha ishga kelib-ketishi va bajargan tasklari haqida ma’lumot chiqishi kerak.
     * Xar bir xodimga ma’lum bir oylik ish haqi belgilanadi.
     * Xodimga oylik berilgach sistemaga qayd etib boriladi.
     * Xodim bo’yicha yoki belgilagan oy bo’yicha berilgan oyliklarni ko’rish imkoniyati bo’lishi kerak.
     * Rahbariyat xodimlarga berilgan vazifalarni o’z vaqtida bajargani yoki o’z vaqtida bajara olmayotgani xaqida malumotlarni ko’ra olishi kerak.
     *
     */

    public static void main(String[] args) {
        SpringApplication.run(SpringBootJwtRealProjectUserInDbApplication.class, args);


    }

}
