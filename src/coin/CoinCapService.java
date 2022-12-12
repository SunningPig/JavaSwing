package coin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import facecat.topin.core.*;

/*
* 数据服务
*/
public class CoinCapService {
    /*
    * 获取网页数据
    */
    public static String get(String url)
    {
        String result = "";
        BufferedReader reader = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "text/html");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /*
    * 获取市场信息
    */
    //api.coincap.io/v2/assets/bitcoin/history?interval=d1
    public static ArrayList<CoinMarketInfo> getMarketInfos(String exchangeID, String currentPari) {
        ArrayList<CoinMarketInfo> datas = new ArrayList<CoinMarketInfo>();
        String url = "https://api.coincap.io/v2/markets?limit=2000";
        if (exchangeID.length() > 0)
        {
            url +="&exchangeId=" + exchangeID;
        }
        else if (currentPari.length() > 0)
        {
            String[] strs = currentPari.split("[/]");
            url += "&baseSymbol=" + strs[0] + "&quoteSymbol" + strs[1];
        }
        String result = get(url);
        String tag1 = "exchangeId\":\"";
        String tag2 = "rank\":\"";
        String tag3 = "baseSymbol\":\"";
        String tag4 = "baseId\":\"";
        String tag5 = "quoteSymbol\":\"";
        String tag6 = "quoteId\":";
        String tag7 = "priceQuote\":";
        String tag8 = "priceUsd\":";
        String tag9 = "volumeUsd24Hr\":";
        String tag10 = "percentExchangeVolume\":";
        String tag11 = "tradesCount24Hr\":";
        String tag12 = "updated\":";
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);
        tags.add(tag5);
        tags.add(tag6);
        tags.add(tag7);
        tags.add(tag8);
        tags.add(tag9);
        tags.add(tag10);
        tags.add(tag11);
        tags.add(tag12);
        int offset = 0;
        boolean isLoop = true;
        while (isLoop) {
            CoinMarketInfo marketInfo = new CoinMarketInfo();
            for (int i = 0; i < tags.size(); i++) {
                int tagIndex = result.indexOf(tags.get(i), offset);
                if (tagIndex == -1) {
                    isLoop = false;
                    break;
                }
                char ch = '\"';
                if (i == 11) {
                    ch = '}';
                }
                String tagStr = result.substring(tagIndex + tags.get(i).length(),
                    result.indexOf(ch, tagIndex + tags.get(i).length() + 2)).replace("\"", "").replace(",", "");
                switch (i) {
                    case 0:
                        marketInfo.m_exchangeId = tagStr;
                        break;
                    case 1:
                        marketInfo.m_rank = tagStr;
                        break;
                    case 2:
                        marketInfo.m_baseSymbol = tagStr;
                        break;
                    case 3:
                        marketInfo.m_baseId = tagStr;
                        break;
                    case 4:
                        marketInfo.m_quoteSymbol = tagStr;
                        break;
                    case 5:
                        marketInfo.m_quoteId = tagStr;
                        break;
                    case 6:
                        marketInfo.m_priceQuote = tagStr;
                        break;
                    case 7:
                        marketInfo.m_priceUsd = tagStr;
                        break;
                    case 8:
                        marketInfo.m_volumeUsd24Hr = tagStr;
                        break;
                    case 9:
                        marketInfo.m_percentExchangeVolume = tagStr;
                        break;
                    case 10:
                        marketInfo.m_tradesCount24Hr = tagStr;
                        break;
                    case 11:
                        marketInfo.m_updated = FCTran.strToDouble(tagStr);
                        break;
                }
                offset = tagIndex + 2;
            }
            if (isLoop) {
                datas.add(marketInfo);
            }
        }
        return datas;
    }

    /*
    * 获取交易所信息
    */
    public static ArrayList<CoinExchangeInfo> getExchangeInfos() {
        ArrayList<CoinExchangeInfo> datas = new ArrayList<CoinExchangeInfo>();
        String url = "https://api.coincap.io/v2/exchanges?limit=2000";
        String result = get(url);
        String tag1 = "exchangeId\":\"";
        String tag2 = "name\":\"";
        String tag3 = "rank\":\"";
        String tag4 = "percentTotalVolume\":\"";
        String tag5 = "volumeUsd\":\"";
        String tag6 = "socket\":";
        String tag7 = "exchangeUrl\":";
        String tag8 = "updated\":";
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);
        tags.add(tag5);
        tags.add(tag6);
        tags.add(tag7);
        tags.add(tag8);
        int offset = 0;
        boolean isLoop = true;
        while (isLoop) {
            CoinExchangeInfo exchangeInfo = new CoinExchangeInfo();
            for (int i = 0; i < tags.size(); i++) {
                int tagIndex = result.indexOf(tags.get(i), offset);
                if (tagIndex == -1) {
                    isLoop = false;
                    break;
                }
                char ch = '\"';
                if (i == 11) {
                    ch = '}';
                }
                String tagStr = result.substring(tagIndex + tags.get(i).length(),
                    result.indexOf(ch, tagIndex + tags.get(i).length() + 2)).replace("\"", "").replace(",", "");
                switch (i) {
                    case 0:
                        exchangeInfo.m_exchangeId = tagStr;
                        break;
                    case 1:
                        exchangeInfo.m_name = tagStr;
                        break;
                    case 2:
                        exchangeInfo.m_rank = tagStr;
                        break;
                    case 3:
                        exchangeInfo.m_percentTotalVolume = tagStr;
                        break;
                    case 4:
                        exchangeInfo.m_volumeUsd = tagStr;
                        break;
                    case 5:
                        exchangeInfo.m_tradingPairs = tagStr;
                        break;
                    case 6:
                        exchangeInfo.m_socket = tagStr;
                        break;
                    case 7:
                        exchangeInfo.m_exchangeUrl = tagStr;
                        break;
                    case 8:
                        exchangeInfo.m_updated = FCTran.strToDouble(tagStr);
                        break;
                }
                offset = tagIndex + 2;
            }
            if (isLoop) {
                datas.add(exchangeInfo);
            }
        }
        return datas;
    }

    /*
    * 获取占比信息
    */
    public static ArrayList<CoinRateInfo> getRateInfos()
    {
        ArrayList<CoinRateInfo> datas = new ArrayList<CoinRateInfo>();
        String url = "https://api.coincap.io/v2/rates";
        String result = get(url);
        String tag1 = "id\":\"";
        String tag2 = "symbol\":\"";
        String tag3 = "currencySymbol\":\"";
        String tag4 = "rateUsd\":\"";
        String tag5 = "type\":\""; ;
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);
        tags.add(tag5);
        int offset = 0;
        boolean isLoop = true;
        while (isLoop)
        {
            CoinRateInfo assetInfo = new CoinRateInfo();
            for (int i = 0; i < tags.size(); i++) {
                int tagIndex = result.indexOf(tags.get(i), offset);
                if (tagIndex == -1) {
                    isLoop = false;
                    break;
                }
                char ch = '\"';
                if (i == 11) {
                    ch = '}';
                }
                String tagStr = result.substring(tagIndex + tags.get(i).length(),
                    result.indexOf(ch, tagIndex + tags.get(i).length() + 2)).replace("\"", "").replace(",", "");
                switch (i)
                {
                    case 0:
                        assetInfo.m_id = tagStr;
                        break;
                    case 1:
                        assetInfo.m_symbol = tagStr;
                        break;
                    case 2:
                        assetInfo.m_currencySymbol = tagStr;
                        break;
                    case 3:
                        assetInfo.m_rateUsd = tagStr;
                        break;
                    case 4:
                        assetInfo.m_type = tagStr;
                        break;
                }
                offset = tagIndex + 2;
            }
            if (isLoop)
            {
                datas.add(assetInfo);
            }
        }
        return datas;
    }

    /*
    * 获取多个资产信息
    */
    public static ArrayList<CoinAssetInfo> getAssetInfos() {
        ArrayList<CoinAssetInfo> datas = new ArrayList<CoinAssetInfo>();
        String url = "https://api.coincap.io/v2/assets?limit=2000";
        //URLDownloadToFile(IntPtr.Zero, url, Application.StartupPath + "\\assests", 0, IntPtr.Zero);
        String result = get(url);
        String tag1 = "id\":\"";
        String tag2 = "rank\":\"";
        String tag3 = "symbol\":\"";
        String tag4 = "name\":\"";
        String tag5 = "supply\":\"";
        String tag6 = "maxSupply\":";
        String tag7 = "marketCapUsd\":";
        String tag8 = "volumeUsd24Hr\":";
        String tag9 = "priceUsd\":";
        String tag10 = "changePercent24Hr\":";
        String tag11 = "vwap24Hr\":";;
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);
        tags.add(tag5);
        tags.add(tag6);
        tags.add(tag7);
        tags.add(tag8);
        tags.add(tag9);
        tags.add(tag10);
        tags.add(tag11);
        int offset = 0;
        boolean isLoop = true;
        while (isLoop) {
            CoinAssetInfo assetInfo = new CoinAssetInfo();
            for (int i = 0; i < tags.size(); i++) {
                int tagIndex = result.indexOf(tags.get(i), offset);
                if (tagIndex == -1) {
                    isLoop = false;
                    break;
                }
                char ch = '\"';
                if (i == 11) {
                    ch = '}';
                }
                String tagStr = result.substring(tagIndex + tags.get(i).length(),
                    result.indexOf(ch, tagIndex + tags.get(i).length() + 2)).replace("\"", "").replace(",", "");
                switch (i) {
                    case 0:
                        assetInfo.m_id = tagStr;
                        break;
                    case 1:
                        assetInfo.m_rank = tagStr;
                        break;
                    case 2:
                        assetInfo.m_symbol = tagStr;
                        break;
                    case 3:
                        assetInfo.m_name = tagStr;
                        break;
                    case 4:
                        assetInfo.m_supply = tagStr;
                        break;
                    case 5:
                        assetInfo.m_maxSupply = tagStr;
                        break;
                    case 6:
                        assetInfo.m_marketCapUsd = tagStr;
                        break;
                    case 7:
                        assetInfo.m_volumeUsd24Hr = tagStr;
                        break;
                    case 8:
                        assetInfo.m_priceUsd = tagStr;
                        break;
                    case 9:
                        assetInfo.m_changePercent24Hr = tagStr;
                        break;
                    case 10:
                        assetInfo.m_vwap24Hr = tagStr;
                        break;
                }
                offset = tagIndex + 2;
            }
            if (isLoop) {
                datas.add(assetInfo);
            }
        }
        return datas;
    }

    /*
    * 获取多个资产信息
    */
    //String url = String.Format("https://api.coincap.io/v2/candles?exchange=poloniex&interval={0}&baseId=ethereum&quoteId=bitcoin", interval);
    public static ArrayList<CoinSecurityData> getCoinSecurityDatas(String exchange, String interval, String baseId, String quoteId) {
        ArrayList<CoinSecurityData> datas = new ArrayList<CoinSecurityData>();
        String url = "https://api.coincap.io/v2/candles?exchange=" + exchange + "&interval=" + interval + "&baseId=" + baseId + "&quoteId=" + quoteId;
        String result = get(url);
        String tag1 = "open\":\"";
        String tag2 = "high\":\"";
        String tag3 = "low\":\"";
        String tag4 = "close\":\"";
        String tag5 = "volume\":\"";
        String tag6 = "period\":";
        ArrayList<String> tags = new ArrayList<String>();
        tags.add(tag1);
        tags.add(tag2);
        tags.add(tag3);
        tags.add(tag4);
        tags.add(tag5);
        tags.add(tag6);
        int offset = 0;
        boolean isLoop = true;
        while (isLoop) {
            CoinSecurityData securityData = new CoinSecurityData();
            for (int i = 0; i < tags.size(); i++) {
                int tagIndex = result.indexOf(tags.get(i), offset);
                    if (tagIndex == -1) {
                        isLoop = false;
                        break;
                    }
                    char ch = '\"';
                    if (i == 5) {
                        ch = '}';
                    }
                 String tagStr = result.substring(tagIndex + tags.get(i).length(),
                        result.indexOf(ch, tagIndex + tags.get(i).length() + 2));
                switch (i) {
                    case 0:
                        securityData.m_open = FCTran.strToDouble(tagStr);
                        break;
                    case 1:
                        securityData.m_high = FCTran.strToDouble(tagStr);
                        break;
                    case 2:
                        securityData.m_low = FCTran.strToDouble(tagStr);
                        break;
                    case 3:
                        securityData.m_close = FCTran.strToDouble(tagStr);
                        break;
                    case 4:
                        securityData.m_volume = FCTran.strToDouble(tagStr);
                        break;
                    case 5:
                        securityData.m_date = FCTran.strToDouble(tagStr) / 1000;
                        break;
                }
                offset = tagIndex + 2;
            }
            if (isLoop) {
                datas.add(securityData);
            }
        }
        return datas;
    }
}
