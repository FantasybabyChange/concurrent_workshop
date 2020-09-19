package com.fantasybaby.concurrent.stm.mvcc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**数据引用对象
 * 维护版本号
 * @author: liuxi
 * @time: 2019/11/18 10:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionRef<T> {
    long version;
    T value;
}
