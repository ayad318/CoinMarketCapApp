package major_project.model.input;

import java.util.ArrayList;
import java.util.List;

import major_project.model.Crypto;
import major_project.model.Observer;
import major_project.model.ToAddData;
import major_project.model.apiWrapper;

public class MainEngine implements InputEngine {

    ArrayList<Crypto> CryptoList = new ArrayList<Crypto>();
    private final List<Observer> observers = new ArrayList<>();

    private final apiWrapper api;

    private float threshold = -1;

    public MainEngine(apiWrapper api) {
        this.api = api;
    }

    @Override
    public ToAddData getCryptoCurrencies() {
        return api.getCryptoCurrencies();
    }

    @Override
    public void AddCrypto(int id) {

        Crypto item = api.getCryptoMetaData(id);
        for (Crypto i : CryptoList) {
            if (i.getId() == item.getId()) {
                item = null;
                return;
            }
        }
        if (item != null) {
            CryptoList.add(item);
        }

        updateObservers();
    }

    public void addObserver(Observer observer) {

        observers.add(observer);
    }

    private void updateObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    @Override
    public ArrayList<Crypto> getCryptoList() {
        return CryptoList;
    }

    @Override
    public void RemoveCrypto(Crypto crypto) {
        CryptoList.remove(crypto);
        updateObservers();
    }

    @Override
    public void ClearCrypto() {
        CryptoList.clear();
        updateObservers();
    }

    @Override
    public float getConversionrate(Crypto x, Crypto y) {
        if (x == null || y == null)
            throw new IllegalArgumentException();
        return api.getCryptoPrice(x.getId(), y.getId());
    }

    @Override
    public float getFinishingvalue(float input, Crypto x, Crypto y) {
        if (x == null || y == null)
            throw new IllegalArgumentException();

        return input * getConversionrate(x, y);
    }

    @Override
    public void setThreshold(float threshold) {

        this.threshold = threshold;

    }

    @Override
    public float getThreshold() {

        return threshold;
    }

    @Override
    public boolean isConversionRateValid(Crypto x, Crypto y) {
        float conversionRate = getConversionrate(x, y);

        // threshold check return negative finishing value whihc is impossible
        if (conversionRate < threshold) {
            return false;
        }
        return true;
    }

}
