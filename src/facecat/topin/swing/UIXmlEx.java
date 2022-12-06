/*
* FaceCat图形通讯框架(非开源)
* 著作权编号:2015SR229355
* 上海卷卷猫信息技术有限公司
*/

package facecat.topin.swing;

import facecat.topin.core.*;
import facecat.topin.input.*;
import facecat.topin.btn.*;
import facecat.topin.xml.*;

import org.w3c.dom.*;

public class UIXmlEx extends FCUIXml
{
	/** 
	 创建控件
	 
	 @param node 节点
	 @param type 类型
	 @return 控件
	*/
	@Override
	public FCView createView(Node node, String type)
	{
		FCNative inative = getNative();
		if (type.equals("ribbonbutton"))
		{
			return new RibbonButton();
		}
                else if (type.equals("ribbonbutton2"))
		{
			return new FCButton();
		}
		else
		{
			return super.createView(node, type);
		}
	}
        
        public void load(String xmlPath){
            super.loadXml(xmlPath, null);
        }

        public double m_scaleFactor = 0.5;

        /// <summary>
        /// 重置缩放尺寸
        /// </summary>
        /// <param name="clientSize">客户端大小</param>
        public void resetScaleSize(FCSize clientSize)
        {
            FCNative inative = getNative();
            if (inative != null)
            {
                FCHost host = inative.getHost();
                FCSize nativeSize = inative.getSize();
                inative.setScaleSize(new FCSize((int)(clientSize.cx * m_scaleFactor), (int)(clientSize.cy * m_scaleFactor)));
                inative.update();
            }
        }
}