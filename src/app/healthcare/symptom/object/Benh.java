 /* Copyright [2015] [Nguyễn Hoàng Vũ , Nguyễn Phi Viễn]

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package app.healthcare.symptom.object;

public class Benh {
	private String nameBenh;
	private String benhId;

	public Benh(String nameBenh, String benhId) {
		this.nameBenh = nameBenh;
		this.benhId = benhId;
	}


	public String getNameBenh() {
		return nameBenh;
	}



	public String getBenhId() {
		return benhId;
	}



	@SuppressWarnings("unused")
	private void setFoodName(String nameBenh) {
		this.nameBenh = nameBenh;
	}
}
