# Usterki

Aplikacja do zgłaszania usterek. Ekran 1: formularz (Spinner, opis, SeekBar,
CheckBox) z walidacją. Ekran 2: podsumowanie z potwierdzeniem. Numer zgłoszenia
wraca do ekranu 1 przez ActivityResultLauncher (obsługa potwierdzenia i
rezygnacji). Przycisk Udostępnij używa intencji niejawnej.

![ekran 1]
<img width="1080" height="2400" alt="Screenshot_20261116_012956" src="https://github.com/user-attachments/assets/11f43e24-2277-46fd-b06d-047dea9ab78d" />

![ekran 2]
<img width="1080" height="2400" alt="Screenshot_20261116_013008" src="https://github.com/user-attachments/assets/29c95bcc-43ef-41de-a6c0-c8a1c9caa95c" />
## Intencja jawna a niejawna

Intencja jawna wskazuje konkretną klasę do uruchomienia. U mnie: przejście
z MainActivity do SummaryActivity (new Intent(this, SummaryActivity.class)).
Intencja niejawna opisuje czynność, a system sam szuka aplikacji, która ją
wykona. U mnie: przycisk Udostępnij (ACTION_SEND), otoczony blokiem try na
wypadek braku aplikacji.
