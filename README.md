# 🚦 TrafficLight - Akıllı Trafik Işığı Simülasyonu

JavaFX ile geliştirilmiş, araç yoğunluğuna göre akıllı trafik ışığı kontrolü sağlayan interaktif bir simülasyon sistemidir. Proje, dört yönlü bir kavşakta araç hareketlerini, ışık sürelerini ve yoğunluğu gerçek zamanlı olarak yönetir ve görselleştirir.

---

## 📸 Ekran Görüntüsü

![Intersection Simulation](./src/main/resources/assets/map/intersection2.png)

---

## 🧠 Temel Özellikler

* ✅ Araç yoğunluğuna göre dinamik yeşil ışık süresi belirleme
* ✅ Gerçek zamanlı ışık, yoğunluk ve istatistik görselleştirme
* ✅ Dönüş animasyonları (sağ, sol, düz)
* ✅ Araç türleri (araba, kamyon, minibüs)
* ✅ Gerçek zamanlı çakışma kontrolü
* ✅ Kullanıcı tarafından giriş yapılabilir trafik verileri
* ✅ Modüler ve genişletilebilir yapı

---

## 💠 Teknolojiler

* **Java 21**
* **JavaFX 21**
* **Maven** (bağımlılık yönetimi için)
* **MVC mimarisi**

---

## 🗂️ Proje Yapısı

```
src/
├── main/
│   ├── java/
│   │   ├── com.traffic.controller    # Simülasyon mantığı ve kontrol katmanı
│   │   ├── com.traffic.model         # Enum ve temel model sınıfları
│   │   ├── com.traffic.view          # JavaFX UI sınıfları
│   │   ├── com.traffic.factory       # Araç üretimi (Factory Pattern)
│   │   └── com.traffic.util          # Sabitler ve yardımcı sınıflar
│   └── resources/
│       ├── assets/icons/vehicles     # Araç ikonları (PNG)
│       ├── assets/map                # Yol haritası
│       └── styles.css                # (Opsiyonel) UI stil dosyası
```

---

## 🚀 Kurulum ve Çalıştırma

### Gereksinimler

* [Java 21+](https://adoptium.net/)
* [JavaFX SDK 21+](https://gluonhq.com/products/javafx/)
* [Maven](https://maven.apache.org/)

### 1. JavaFX Ayarları

IDE’ye göre JavaFX SDK'yı tanımlayın (IntelliJ örneği):

```
--module-path "C:\javafx-sdk-21.0.7\lib" --add-modules javafx.controls
```

### 2. Maven ile Derleme ve Çalıştırma

```bash
mvn clean javafx:run
```

---

## ⚙️ Kullanım

1. Sol panelden yönlere ait araç sayılarını girin.
2. "Start" butonuna basarak simülasyonu başlatın.
3. "Pause" veya "Reset" ile kontrol sağlayın.
4. İstatistikler ve yoğunluk grafiklerini takip edin.

---

## 📊 Genişletme Fikirleri

* ⛔ Kırmızı ışıkta geçen araçların cezalandırılması
* 🧠 ML tabanlı trafik tahmin algoritması
* 🌐 Harici trafik verisi entegrasyonu (API)
* 📈 Grafiksel trafik analiz raporları

---

## 👨‍💻 Geliştirici

**Erdal Gümüş**
🔗 [github.com/erdalgumuss](https://github.com/erdalgumuss)

---

## 📁 Lisans

MIT License. Bu projeyi serbestçe kullanabilir ve dağıtabilirsiniz.
