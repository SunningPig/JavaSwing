#通达信日线
#请使用前复权的日K线数据
import os

#缓存数据
class SecurityData:
	code = '' #股票代码
	name = '' #股票名称
	value = 0 #数值
	
#定义列表
lst = []

<html xmlns="facecat">
  <head>
    <script>
		function showKLine(){
			callFunction('myMultiChart', 'loadDatas', '');
		}
		function callSelectStock(){
			return getSelectStock();
		}
    </script>
  </head>
  <body>
    <div dock="fill">
        <div type="custom" cid="multiview" name="myMultiChart" dock="fill" maxCount="50" layoutstyle="toptobottom" showvscrollbar="true" resultDir="{AppPath}\\data\\tdx\\day_result" resultFile="DMI买入.csv" fileField="{代码}.txt">
                <label name="Label" text="{代码}|{名称}" location="14,20" size="100,40" font="Segoe UI,16" autosize="false" textalign="middlecenter" backcolor="-200000000193"/>
					<div type="custom" cid="chartex" size="400,400" name="Chart" text="Chart" location="104,318" >
					<fields>OPEN=1;HIGH=2;LOW=3;CLOSE=4;VOL=5</fields>
                <chartdiv name="Div1" verticalpercent="50" backcolor="-200000000106">
                  <leftvscale/>
                  <rightvscale/>
                  <hscale visible="false" height="0"/>
                  <hgrid/>
                  <vgrid/>
                  <tooltip/>
                  <titlebar/>
                  <crossline/>
                  <candleshape openField="1" highField="2" lowField="3" closeField="4"/>
                </chartdiv>
                <chartdiv name="Div2" verticalpercent="25" backcolor="-200000000106">
                  <leftvscale/>
                  <rightvscale/>
                  <hscale visible="false" height="0"/>
                  <hgrid/>
                  <vgrid/>
                  <tooltip/>
                  <titlebar/>
                  <crossline/>
                  <volshape fieldName="5" openfield="1" closefield="4"/>
                </chartdiv>
                <chartdiv name="Div3" verticalpercent="25" backcolor="-200000000106">
                  <leftvscale/>
                  <rightvscale/>
                  <hscale/>
                  <hgrid/>
                  <vgrid/>
                  <tooltip/>
                  <titlebar/>
                  <crossline/>
                  <polylineshape fieldName="6" color="rgb(200,200,200)" fieldtext="PDI"/>
                  <polylineshape fieldName="7" color="rgb(255,255,0)" fieldtext="MDI"/>
                  <polylineshape fieldName="8" color="rgb(255,0,255)" fieldtext="ADX"/>
                  <polylineshape fieldName="9" color="rgb(0,255,0)" fieldtext="ADXR"/>
                </chartdiv>
              </div>
      </div>
    </div>
  </body>
</html>

#获取选中的股票
selectStocks = callFaceCat('callSelectStock();')
stocks = selectStocks.split(',')
#数据哈希表
allDatas = dict()
for code in stocks:
	newCode = code[code.find('.') + 1:] + code[0 : code.find('.')]
	allDatas[newCode] = ''

#循环遍历所有的日K线文件
for root, dirs, files in os.walk(r'{AppPath}\\data\\tdx\\day'):
	for file in files:
		securityCode = str.replace(file, '.txt', '')
		if ((securityCode in allDatas) or len(stocks) <= 1) and file != '.DS_Store': 
			#写日志
			print('正在计算' + file)
			#打开日K线文件
			fs2 = open(os.path.join(root,file), 'r', True)
			fs3 = open(os.path.join(r'{AppPath}\\data\\tdx\\day_result', file),'a+')
			#索引
			pos = 0
			#数据索引
			dataPos = 0
			#股票名称
			sName = ''
			#交叉类型
			isCross = 0
			#定义周期
			n = 14
			m = 6
			#保存数据的集合
			lstClose = []
			lstHigh = []
			lstLow = []
			lstTr = []
			lstHdd = []
			lstLdd = []
			lstAdxPer = []
			lastAdx = 0
			lstAdx = []
			lastAdxr = 0
			#循环遍历每一行
			while True:
				#读取该行
				line = fs2.readline()
				#没有行的时候退出
				if not line: break
				#去除前2行和尾行
				if pos > 1 and len(line) > 20:
					#重置数量
					isCross = 0
					#分割字符串
					strs = line.split(',')
					#日期
					strDate = strs[0]
					#收盘价
					closePrice = float(strs[4])
					#最高价
					highPrice = float(strs[2])
					#最低价
					lowPrice = float(strs[3])
					#开盘价
					openPrice = float(strs[1])
					#成交量
					volume = float(strs[5])
					#保存到集合
					lstClose.append(closePrice)
					lstHigh.append(highPrice)
					lstLow.append(lowPrice)
					#计算MTR
					rightValue = abs(-lowPrice)
					if dataPos > 0:
						rightValue = abs(lstClose[dataPos - 1] - lowPrice)
					leftValue = abs(highPrice)
					if dataPos > 0:
						if highPrice - lowPrice > abs(highPrice - lstClose[dataPos - 1]):
							leftValue = highPrice - lowPrice
						else:
							leftValue = abs(highPrice - lstClose[dataPos - 1])
					tr = 0
					if leftValue > rightValue:
						tr = leftValue
					else:
						tr = rightValue
					lstTr.append(tr)
					#真实计算周期
					realN = n
					if dataPos < n:
						realN = dataPos  + 1
					#数值的和
					startIndex = dataPos - n + 1
					if startIndex < 0:
						startIndex = 0
					#计算和
					mtr = 0
					thisLst = lstTr[startIndex:dataPos + 1]
					for cVal in thisLst:
						mtr = mtr + cVal
					#计算HD
					hd = highPrice
					if dataPos > 0:
						hd = highPrice - lstHigh[dataPos - 1]
					#计算LD
					ld = -lowPrice
					if dataPos > 0:
						ld = lstLow[dataPos - 1] - lowPrice
					if hd > 0 and hd > ld:
						lstHdd.append(hd)
					else:
						lstHdd.append(0)
					if ld > 0 and ld > hd:
						lstLdd.append(ld)
					else:
						lstLdd.append(0)
					#计算DMP
					dmp = 0
					pPos = dataPos
					while pPos >= startIndex:
						dmp = dmp + lstHdd[pPos]
						pPos = pPos - 1
					#计算DMM
					dmm = 0
					mPos = dataPos
					while mPos >= startIndex:
						dmm = dmm + lstLdd[mPos]
						mPos = mPos - 1
					#计算PDI
					pdi = 0
					if mtr != 0:
						pdi = dmp * 100 / mtr
					#计算MDI
					mdi = 0
					if mtr != 0:
						mdi = dmm * 100 / mtr
					adxPer = 0
					if mdi + pdi != 0:
						adxPer = abs(mdi - pdi) / (mdi + pdi) * 100
					lstAdxPer.append(adxPer)
					#计算ADX
					#真实计算周期
					realN = m
					if dataPos < m:
						realN = dataPos  + 1
					#数值的和
					startIndex = dataPos - m + 1
					if startIndex < 0:
						startIndex = 0
					sum = 0
					if dataPos > m:
						#高性能求和
						sum = lastAdx * m + adxPer - lstAdxPer[startIndex - 1]
					else:
						#计算和
						thisLst = lstAdxPer[startIndex:dataPos + 1]
						for cVal in thisLst:
							sum = sum + cVal
					adx = sum / realN
					lstAdx.append(adx)
					#计算ADXR
					refAdx = lstAdx[0]
					if dataPos > m:
						refAdx = lstAdx[dataPos - m]
					adxr = (adx + refAdx) / 2
					if lastAdx < lastAdxr and adx > adxr and pdi < 30 and mdi < 30:
						isCross = 1
					lastAdxr = adxr
					lastAdx = adx
					#累加数据索引
					dataPos = dataPos + 1
					fs3.write(strDate + ',' + str(openPrice) + ',' + str(highPrice) + ',' + str(lowPrice) + ',' + str(closePrice) + ',' + str(volume) + ',' + str(pdi) + ',' + str(mdi) + ',' + str(adx) + ',' + str(adxr) + '\r\n')
				elif pos == 0:
					sName = line[line.find(' ') + 1 : line.find(' 日线')]
				#累加索引
				pos = pos + 1
			#保存到列表中
			securityData = SecurityData()
			securityData.code = securityCode
			if dataPos > 250:
				securityData.value = isCross
			securityData.name = sName
			lst.append(securityData)
			#关闭文件流
			fs2.close()
			fs3.close()
#给列表排序
from operator import attrgetter
lst2 = sorted(lst, key=attrgetter('value'), reverse=True)

#打开日志文件
fs = open(r'{AppPath}\\data\\tdx\\day_result\\DMI买入.csv','a+')
fs.write("代码,名称\r\n")

#输出结果
count = 0
for val in lst2:
	if val.value == 1:
		print(str(count + 1) + '.' + val.name + ','  + val.code + ',DMI买入')
		fs.write(val.code + ',' + val.name + '\r\n')
		count = count + 1
#关闭文件流
fs.close()

callFaceCat('showKLine();')