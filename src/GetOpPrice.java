/*
 * TradingBot - A Java Trading system..
 * 
 * Copyright (C) 2013 Philipz (philipzheng@gmail.com)
 * http://www.tradingbot.com.tw/
 * http://www.facebook.com/tradingbot
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * Apache License, Version 2.0 ���v���廡��
 * http://www.openfoundry.org/licenses/29
 * �Q�� Apache-2.0 �{��������u���q�ȳW�w
 * http://www.openfoundry.org/tw/legal-column-list/8950-obligations-of-apache-20
 */
//////////////////////////////////////////////////////////////////////////////
// DdeExcelLinkClientMulti
//
// Copyright (c) 1996-2003 Neva Object Technology, Inc  www.nevaobject.com
//
//
// NEVA OBJECT TECHNOLOGY,INC. MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE 
// SUITABILITY OF THIS SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
// TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
// PARTICULAR PURPOSE, OR NON-INFRINGEMENT. NEVA OBJECT TECHNOLOGY,INC.
// SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY ANYBODY AS A RESULT OF 
// USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS DERIVATIVES.
//
//
///////////////////////////////////////////////////////////////////////////////

/*
 The following example establishes a DDE hot link with multiple Microsoft Excel instances to monitor changes
 ���{�������]�w�b8:39������A�ӤU��j�v����b����C
 */

public class GetOpPrice {
	com.neva.DdeClient cl;
	static String item;
	static Double price;

	public static void main(String[] args) {
		System.out.println(GetOpPrice.getvolume(GetWednesday.getSymbol()));
		System.out.println(GetOpPrice.getprice(7400, false));
	}

	void doit() {
		try {
			int timeout = 10000;
			int format = com.neva.DdeUtil.CF_TEXT;
			cl = new com.neva.DdeClient();
			cl.addDdeClientTransactionEventListener(new com.neva.DdeClientTransactionEventAdaptor() {
						public void onAdviseData(
								com.neva.DdeClientTransactionEvent e) {
							String temp = new String(e.getDdeData()).trim();
							if (temp.length() >= 5)
								temp = temp.substring(0, 5);
							price = Double.valueOf(temp);
						}

						public void onDisconnect(
								com.neva.DdeClientTransactionEvent e) {
							System.exit(0);
						}
					});

			// Connect to all topics exposed by running Excel instances
			cl.connectList("PS", "KS"); // PS, DC, CH
			String hotitem = item;
			cl.startAdvise(hotitem, format, timeout);
			while (price == null) {
			}
			cl.stopAdvise(hotitem, format, timeout);
			cl.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public static Double getprice(int input, boolean buy) {
		item = GetWednesday.getOPSymbol(input + "", buy) + ".124";
		new GetOpPrice().doit();
		double tmp = price;
		price = null;
		item = null;
		return tmp;
	}
	
	public static int getvolume(String input) {
		item = input + ".128";
		new GetOpPrice().doit();
		int tmp = price.intValue();
		price = null;
		item = null;
		return tmp;
	}
	
	public static double getchange(String input) {
		item = input + ".125";
		new GetOpPrice().doit();
		double tmp = price;
		price = null;
		item = null;
		return tmp;
	}
	
	public static double getprice(String input) {
		item = input + ".124";
		new GetOpPrice().doit();
		double tmp = price;
		price = null;
		item = null;
		return tmp;
	}
}
/*
 * </PRE> </BODY> </HTML>
 */