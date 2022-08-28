package major_project.model.input;

import java.util.List;

import major_project.model.Crypto;
import major_project.model.Observer;
import major_project.model.ToAddData;

public interface InputEngine {

    ToAddData getCryptoCurrencies();

    void AddCrypto(int id);

    void RemoveCrypto(Crypto crypto);

    void ClearCrypto();

    List<Crypto> getCryptoList();

    void addObserver(Observer observer);

    float getConversionrate(Crypto x, Crypto y);

    float getFinishingvalue(float input, Crypto x, Crypto y);

    boolean isConversionRateValid(Crypto x, Crypto y);

    void setThreshold(float threshold);

    float getThreshold();

}
