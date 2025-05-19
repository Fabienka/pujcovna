
# 📚 Bookache - Book Lending Manager
Toto je školní projekt do předmětu KPRO1 

**Bookache** je desktopová Java aplikace pro správu půjčování knih pomocí přehledného grafického rozhraní (Swing). Aplikace umožňuje přidávat, upravovat, mazat a půjčovat knihy i uživatele. 

---

## 🔧 Funkcionalita

### 📖 Práce s knihami
- Přidání nové knihy
- Úprava informací o knize
- Smazání knihy
- Půjčení knihy vybranému uživateli
- Zobrazení dostupnosti

### 👤 Práce s uživateli
- Přidání nového uživatele
- Úprava jména a e-mailu
- Smazání uživatele
- Vrácení knihy
- Zobrazení zapůjčených knih a posledního data zápůjčky

---

## 🗃️ Datová vrstva

- Knihy: `books.json`
- Uživatelé: `users.json`
- Formát dat: JSON (čtení i zápis pomocí knihovny Gson)

---

## 🧱 Struktura balíčků
- cz/bojdova/
- ├── controller/ // Logika aplikace
- ├── dao/ // Rozhraní pro přístup k datům
- ├── dao/impl/ // Implementace přístupu k datům (JSON)
- ├── model/ // Datové třídy Book, User
- ├── util/ // Pomocné utility (např. IdGenerator)
- ├── view/
- │ ├── MainGUI.java // Hlavní GUI okno
- │ ├── dialog/ // Všechny modální dialogy
- │ └── panel/ // Panely pro záložky knih a uživatelů
- │ └── util/ // Pomocné GUI utility (např. ButtonFactory)


---

## ▶️ Spuštění aplikace

1. **Otevři projekt ve Visual Studio Code nebo IntelliJ.**
2. **Ujisti se, že soubory `books.json` a `users.json` jsou v root složce.**
3. **Spusť `Main.java` nebo `MainGUI.java`.**

---

## 📝 Poznámky

- Aplikace nemá připojení k databázi, vše je uloženo v souborech JSON.
- Pokud je JSON soubor prázdný nebo chybně zapsaný, aplikace zobrazí výchozí chybovou hlášku v konzoli.
- Aplikace si pamatuje poslední `loan date`, i pokud uživatel žádnou knihu aktuálně nemá.
- Aplikace neumí vracet více knih najednou

---

**Autor**: Nela Bojdová
**Jazyk**: Java 
**Framework**: Swing (GUI)



