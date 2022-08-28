package major_project.model.input;

import java.util.ArrayList;
import java.util.List;

import major_project.model.Crypto;
import major_project.model.Observer;
import major_project.model.ToAddCurrency;
import major_project.model.ToAddData;
import major_project.model.URLS;

public class DummyEngine implements InputEngine {

    List<Crypto> list = new ArrayList<Crypto>();
    private final List<Observer> observers = new ArrayList<>();

    @Override
    public ToAddData getCryptoCurrencies() {
        ArrayList<ToAddCurrency> list = new ArrayList<ToAddCurrency>();
        for (int i = 1; i < 10; i++) {
            list.add(new ToAddCurrency(i, "Dummy", "DUMP"));
        }
        return new ToAddData(list.toArray(ToAddCurrency[]::new));
    }

    @Override
    public void AddCrypto(int id) {
        list.add(new Crypto(id, "https://s2.coinmarketcap.com/static/img/coins/64x64/11557.png", "DUMMY COIN", "DUMP",
                "DUMMY useless description", "today", new URLS(new String[] { "www.dummy.com" })));
        updateObservers();
    }

    @Override
    public List<Crypto> getCryptoList() {

        return list;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);

    }

    private void updateObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public void RemoveCrypto(Crypto crypto) {
        list.remove(crypto);
        updateObservers();
    }

    @Override
    public void ClearCrypto() {
        list.clear();
        updateObservers();
    }

    @Override
    public float getConversionrate(Crypto x, Crypto y) {

        return (float) 1.5;
    }

    @Override
    public float getFinishingvalue(float input, Crypto x, Crypto y) {

        return 99;
    }

    @Override
    public void setThreshold(float threshold) {

    }

    @Override
    public float getThreshold() {

        return 999;
    }

    @Override
    public boolean isConversionRateValid(Crypto x, Crypto y) {

        return true;
    }

}
