/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package choose;
import facecat.topin.core.*;
import java.util.*;
import facecat.topin.service.*;
import facecat.topin.sock.*;

public class SGlobalService extends FCClientService {
    /// <summary>
    /// 创建登录服务
    /// </summary>
    public SGlobalService()
    {
        setServiceID(SERVICEID_GLOBAL);
        setCompressType(COMPRESSTYPE_GZIP);
    }
    /// <summary>
    /// 指标服务ID
    /// </summary>
    public static int SERVICEID_GLOBAL = 10000;

    /// <summary>
    /// 获取股票代码
    /// </summary>
    public static int FUNCTIONID_GLOBAL_GETCODES = 2;

    /// <summary>
    /// 获取股票代码
    /// </summary>
    public static int FUNCTIONID_GLOBAL_GETVALUES = 4;

    /// <summary>
    /// 获取涨跌幅情况
    /// </summary>
    public static int FUNCTIONID_GLOBAL_GETUPDOWNINFO = 7;

    /// <summary>
    /// 获取涨跌幅情况
    /// </summary>
    public static int FUNCTIONID_GLOBAL_GETSTOCKCODE = 8;

    /// <summary>
    /// 获取涨跌停信息
    /// </summary>
    /// <param name="upDownInfos"></param>
    /// <returns></returns>
    public int getUpDownInfo(ArrayList<String> filterCodes, int n, ArrayList<UpDownInfo> upDownInfos)
    {
        try { int socketID = FCClientService.connectToServer(0, "1.116.142.147", 9959, "", 0, "", "", "", 6000);
            if (socketID != -1) {
                FCBinary bw = new FCBinary();
                int filterCodesSize = filterCodes.size();
                bw.writeInt(filterCodesSize);
                for (int i = 0; i < filterCodesSize; i++) {
                    bw.writeString(filterCodes.get(i));
                }
                bw.writeInt(n);
                byte[] bytes = bw.getBytes();
                int requestID = getRequestID();
                FCMessage retMsg = new FCMessage();
                addWait(requestID, retMsg);
                int ret = send(new FCMessage(getServiceID(), FUNCTIONID_GLOBAL_GETUPDOWNINFO, requestID, socketID, 0, getCompressType(), bytes.length, bytes));
                bw.close();
                if (waitMessage(requestID, 30000) > 0) {
                    if (retMsg.m_bodyLength > 1) {
                        FCBinary br = new FCBinary();
                        br.write(retMsg.m_body, retMsg.m_bodyLength);
                        int count = br.readInt();
                        for (int i = 0; i < count; i++) {
                            UpDownInfo upDownInfo = new UpDownInfo();
                            upDownInfo.m_date = br.readDouble();
                            upDownInfo.m_avgRange = br.readDouble();
                            upDownInfo.m_allCount = br.readDouble();
                            upDownInfo.m_upCount = br.readDouble();
                            upDownInfo.m_downCount = br.readDouble();
                            upDownInfo.m_amount = br.readDouble();
                            upDownInfo.m_volume = br.readDouble();
                            upDownInfo.m_avgRange2 = br.readDouble();
                            upDownInfos.add(upDownInfo);
                        }
                        br.close();
                        return 1;
                    }
                }
                FCClientSockets.close(socketID);
            }
        }catch (Exception ex){
        }
        return -1;
    }
}
