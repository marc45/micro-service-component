/**
 * Copyright (c) 2011-2014, hubin (243194995@qq.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package wang.leq.kissojfinal;

import wang.leq.sso.Token;


/**
 * 自定义 token
 * <p>
 * @author   hubin
 * @date	 2015年3月11日 
 * @version  1.0.0
 */
public class JToken extends Token {

	private long userId;


	public long getUserId() {
		return userId;
	}


	public void setUserId( long userId ) {
		this.userId = userId;
	}

}
