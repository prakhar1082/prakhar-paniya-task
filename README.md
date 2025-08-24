# 📊 Portfolio Holdings App

An Android application built with **Kotlin** and **MVVM architecture** that displays stock holdings, calculates portfolio summary, and handles loading/error states gracefully.  

This project demonstrates **Clean Architecture principles**, **separation of concerns**, and **best practices** in Android development.

---

## ✨ Features

- ✅ Fetch holdings data from API  
- ✅ Display list of stocks with `RecyclerView`  
- ✅ Expand/Collapse summary section  
- ✅ Calculate **Total Investment, Current Value, Total P&L, Today’s P&L**  
- ✅ Loading & Error states with **Retry** support  
- ✅ Material Design UI components  

---

## 📱 Screenshots  
1. UX when connected to Internet
<img width="350" height="750" alt="Screenshot_20250824_191828" src="https://github.com/user-attachments/assets/1c36cd32-5f7e-40d8-b2f6-8608bb8186b5" />


_______________________

2. UX when not connected to Internet
<img width="350" height="750" alt="Screenshot_20250824_191756" src="https://github.com/user-attachments/assets/cc2de768-f395-412a-954c-9323fa40b2e6" />



---

## 🛠 Tech Stack

- **Language**: Kotlin  
- **Architecture**: MVVM + UseCases  
- **UI**: RecyclerView, Material Components  
- **Asynchronous**: Coroutines, LiveData  
- **Networking**: Retrofit  
- **Dependency Injection**: (Optional, mention Hilt/Koin if you added)  

---

## 📂 Project Structure

```bash
com.example.portfolio
│── data
│   ├── model           # Data models (Holding, PortfolioSummary)
│   ├── remote          # API services (Retrofit)
│   └── repository      # Data sources
│
│── domain
│   └── usecase         # Business logic (CalculateSummaryUseCase)
│
│── ui
│   ├── adapter         # RecyclerView adapters
│   ├── main            # Main Activity/Fragment
│   └── summary         # Portfolio summary UI
│
│── utils               # Extensions, helpers
