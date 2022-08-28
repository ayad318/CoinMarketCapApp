package major_project.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.zxing.oned.ITFWriter;

import major_project.model.input.InputEngine;
import major_project.model.input.MainEngine;
import major_project.model.output.OutputEngine;
import major_project.model.output.mainOutputEngine;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainEngineTest {
    private apiWrapper mock;
    private InputEngine engine;
    private OutputEngine engine_2;
    private Crypto item;

    @BeforeEach
    void setup() {
        mock = mock(apiWrapper.class);
        item = new Crypto(1, "https://s2.coinmarketcap.com/static/img/coins/64x64/1.png", "Bitcoin", "BTC",
                "bitcoin descrition", "date_launched", new URLS(new String[] { "https://bitcoin.org/" }));
        ToAddCurrency test1 = new ToAddCurrency(1, "bitcoin", "BTC");
        ToAddCurrency test2 = new ToAddCurrency(1027, "etheruem", "ETH");
        ToAddData test = new ToAddData(new ToAddCurrency[] { test1, test2 });
        when(mock.getCryptoCurrencies()).thenReturn(test);
        when(mock.getCryptoMetaData(anyInt()))
                .thenReturn(item);
        when(mock.getCryptoPrice(anyInt(), anyInt())).thenReturn((float) 2.0);
        when(mock.postImage(anyString())).thenReturn("https://i.imgur.com/2m2xPWK.png");
        engine = new MainEngine(mock);
        engine_2 = new mainOutputEngine(mock);
    }

    @Test
    void testGetCrypto() {
        ToAddCurrency test1 = new ToAddCurrency(1, "bitcoin", "BTC");
        ToAddCurrency test2 = new ToAddCurrency(1027, "etheruem", "ETH");
        ToAddData test = new ToAddData(new ToAddCurrency[] { test1, test2 });
        when(mock.getCryptoCurrencies()).thenReturn(test);
        assertEquals(test, engine.getCryptoCurrencies());
    }

    @Test
    void testAddCypto() {
        assertTrue(engine.getCryptoList().isEmpty());
        engine.AddCrypto(4);

        assertFalse(engine.getCryptoList().isEmpty());
        assertEquals(item.getId(), engine.getCryptoList().get(0).getId());
        assertEquals(item.getDescription(), engine.getCryptoList().get(0).getDescription());
        assertEquals(item.getName(), engine.getCryptoList().get(0).getName());
        assertEquals(item.getDate_launched(), engine.getCryptoList().get(0).getDate_launched());
        assertEquals(1, engine.getCryptoList().size());
        engine.AddCrypto(3);
        assertEquals(1, engine.getCryptoList().size());
    }

    @Test
    void testRemoveCypto() {
        assertTrue(engine.getCryptoList().isEmpty());
        engine.AddCrypto(4);

        engine.RemoveCrypto(null);
        assertFalse(engine.getCryptoList().isEmpty());
        engine.RemoveCrypto(item);
        assertTrue(engine.getCryptoList().isEmpty());
        engine.RemoveCrypto(item);
        assertTrue(engine.getCryptoList().isEmpty());
    }

    @Test
    void testClearCypto() {
        assertTrue(engine.getCryptoList().isEmpty());
        engine.AddCrypto(4);
        engine.ClearCrypto();
        assertTrue(engine.getCryptoList().isEmpty());
        engine.AddCrypto(4);
        engine.AddCrypto(3);
        engine.AddCrypto(2);
        assertFalse(engine.getCryptoList().isEmpty());
        engine.ClearCrypto();
        assertTrue(engine.getCryptoList().isEmpty());
    }

    @Test
    void testAddRemoveClear() {

        assertTrue(engine.getCryptoList().isEmpty());
        engine.AddCrypto(4);

        assertFalse(engine.getCryptoList().isEmpty());
        assertEquals(item.getId(), engine.getCryptoList().get(0).getId());
        assertEquals(item.getDescription(), engine.getCryptoList().get(0).getDescription());
        assertEquals(item.getName(), engine.getCryptoList().get(0).getName());
        assertEquals(item.getDate_launched(), engine.getCryptoList().get(0).getDate_launched());
        assertEquals(1, engine.getCryptoList().size());
        engine.AddCrypto(3);
        assertEquals(1, engine.getCryptoList().size());
        engine.RemoveCrypto(item);
        assertTrue(engine.getCryptoList().isEmpty());
        engine.AddCrypto(3);
        assertEquals(1, engine.getCryptoList().size());
        engine.ClearCrypto();
        assertTrue(engine.getCryptoList().isEmpty());
        assertEquals("https://i.imgur.com/2m2xPWK.png", engine_2.PostImage("any"));

    }

    @Test
    void testConversionRateTest() {
        assertEquals(2.0, engine.getConversionrate(item, item));
        assertThrows(IllegalArgumentException.class, () -> {
            engine.getConversionrate(null, item);
        }, "Crypto must not be null");
        assertThrows(IllegalArgumentException.class, () -> {
            engine.getConversionrate(item, null);
        }, "Crypto must not be null");
        assertThrows(IllegalArgumentException.class, () -> {
            engine.getConversionrate(null, null);
        }, "Crypto must not be null");

    }

    @Test
    void testFinishingValue() {

        assertEquals(4.0, engine.getFinishingvalue((float) 2.0, item, item));
        assertEquals(0.0, engine.getFinishingvalue((float) 0, item, item));
        assertEquals(-2, engine.getFinishingvalue((float) -1, item, item));
        assertThrows(IllegalArgumentException.class, () -> {
            engine.getFinishingvalue((float) 2.0, null, item);
        }, "Crypto must not be null");
        assertThrows(IllegalArgumentException.class, () -> {
            engine.getFinishingvalue((float) 2.0, item, null);
        }, "Crypto must not be null");
        assertThrows(IllegalArgumentException.class, () -> {
            engine.getFinishingvalue((float) 2.0, null, null);
        }, "Crypto must not be null");

    }

    @Test
    void testPostImageTest() {
        assertEquals("https://i.imgur.com/2m2xPWK.png", engine_2.PostImage("test"));
        assertNull(engine_2.PostImage(null));
    }

    @Test
    void setgetThreshold() {
        engine.setThreshold((float) 0.5);
        assertEquals((float) 0.5, engine.getThreshold());
        engine.setThreshold((float) 1.0);
        assertEquals((float) 1.0, engine.getThreshold());
        engine.setThreshold((float) -99999);
        assertEquals((float) -99999, engine.getThreshold());
        engine.setThreshold((float) 99999);
        assertEquals((float) 99999, engine.getThreshold());
        engine.setThreshold((float) 0);
        assertEquals((float) 0, engine.getThreshold());
    }

    @Test
    void testisConversionRateValid() {
        // engine will return a conversion rate of 2.0
        engine.setThreshold((float) 0.2);
        assertTrue(engine.isConversionRateValid(item, item));
        engine.setThreshold((float) 1.0);
        assertTrue(engine.isConversionRateValid(item, item));
        engine.setThreshold((float) 0.1);
        assertTrue(engine.isConversionRateValid(item, item));

        when(mock.getCryptoPrice(anyInt(), anyInt())).thenReturn((float) 0.4);
        // engine will now return a conversion rate of 0.4
        // conversion rate will be invalid < threshold
        engine.setThreshold((float) 1.0);
        assertFalse(engine.isConversionRateValid(item, item));
        engine.setThreshold((float) 0.7);
        assertFalse(engine.isConversionRateValid(item, item));
        engine.setThreshold((float) 0.41);
        assertFalse(engine.isConversionRateValid(item, item));

        // conversion rate will be valid > threshold
        engine.setThreshold((float) 0.4);
        assertTrue(engine.isConversionRateValid(item, item));
        engine.setThreshold((float) 0.1);
        assertTrue(engine.isConversionRateValid(item, item));
        engine.setThreshold((float) 0.3);
        assertTrue(engine.isConversionRateValid(item, item));
    }
}
