# Merkez Bankasından döviz kurlarını sorgular
--------------------------

Merkez bankasından döviz bilgilerini çekmek için bazı methodlar kullanılmıştır. <br>

Master branchinde merkez bankasından çekilen bilgileri redis kullanarak belirli bir süreliğine cachelemektedir. Bu sayede o süre zarfında atılan isteklerin sonuçları cacheden gelip merkez bankasına istek atmamaktadır. <br>
Master branchini çalıştırmak için öncelikle "docker-compose up" komutu ile docker çalıştırıldıktan sonra proje çalıştırılmalıdır. <br>

without_redis_docker branchinde redis ve docker kullanılmadan yapılmıştır. Her istek merkez bankasına gidip cevap dönmektedir. <br>

--------------

**1- Döviz kurlarının döviz kodlarına göre aynı gün için sorgulayabileceğimiz Rest Web API geliştirilmiştir.**

Örnek istek
http://localhost:8080/api/getExchangeRatesListToday  
Burada tüm kurların sonucu dönmektedir.  

----------
**2- İstenilen Döviz kurunu aynı gün için sorgulayabileceğimiz Rest Web API geliştirilmiştir.**  
Örnek istek : http://localhost:8080/api/getExchangeRatesListTodayByCurrencyCode/USD  
Burada ilgili döviz kodunun bilgileri döner.  

-------------
**3- Tarih e göre (son 5 yıl içinde) ilgili döviz kurunun sorgulanabileceği Rest Web API geliştirilmiştir.** 

date ve currencyCode request param ile alınarak işlem yapılmıştır.  
Örnek istek: http://localhost:8080/api/getExchangeRateByDateAndCurrencyCode?date=16/02/2023&currencyCode=USD  
Burada istenilen tarihe göre ilgili döviz kuru sorgulanmıştır.  

----------
**4- Belirli tarih aralığında istenilen kurun en yüksek kur değerini dönen Rest Web API
geliştirilmiştir.**  

Burada startDate, endDate ve currencyCode request param ile istek atılmaktadır.  
**En yüksek değeri için ForexSelling baz alınmıştır.**  
Örnek istek: http://localhost:8080/api/getMaxExchangeRateByCurrencyCode?startDate=16/02/2023&endDate=18/02/2023&currencyCode=USD  
Dönen sonuç kurun en yük değeridir.

------------
Unit ve integration testleri yazılmıştır.


**Not: Eğer haftasonu ise merkez bankası sonuç olarak null dönmektedir ve bu bilgi log olarak yazdırılmaktadır. Max kur değeri için log yazdırılmaktadır, çünkü en yüksek kur değerini bulmaya çalıştığı için log yazdırmadan hızlı bir şekilde işlem yapması amaçlanmıştır**  

---------------



