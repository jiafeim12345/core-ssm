package me.cloudcat.develop.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author ZZWang
 * @Time 2017年1月17日 下午2:37:43
 *
 */
@SuppressWarnings("rawtypes")
@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity> implements Comparable<Object>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5645931575055794188L;
	
	@Id
	/*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_STORE")*/
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 排序
	 */
	@Override
	public int compareTo(Object obj) {
		if (obj == null) {
			return -1; 
		}
		if (!obj.getClass().getName().equals(getClass().getName())) {
			return  getClass().getName().hashCode()-obj.getClass().getName().hashCode();
		}
		@SuppressWarnings("unchecked")
		T o = (T) obj;
		if (equals(o)) {
			return 0;
		}
		if (o.getId() == null) {
			if (getId() != null) {
				return 1;
			}
		}
		if (o.getId() != null) {
			if (getId() == null) {
				return -1;
			}
		}
		if (o.getId() == null && id == null) {
			return o.hashCode() - this.hashCode();
		}
		//根据id倒序排列
		return o.getId().intValue() - this.getId().intValue();
	}
}
