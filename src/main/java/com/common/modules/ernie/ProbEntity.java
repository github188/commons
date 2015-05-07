package com.common.modules.ernie;

/**
 * 概率输入
 * @Description: TODO
 * @ClassName: ProbEntity 
 * @author huangzy@gzjp.cn
 * @date 2015年5月19日 下午3:44:22
 */
public class ProbEntity<T> {
	//概率
	private Float prob;

	//返回的奖品
	private T prize;

	public ProbEntity(Float prob, T prize) {
		this.prob = prob;
		this.prize = prize;
	}

	public Float getProb() {
		return prob;
	}

	public void setProb(Float prob) {
		this.prob = prob;
	}

	public T getPrize() {
		return prize;
	}

	@Override
	public String toString() {
		return "ProbEntity [prob=" + prob + ", prize=" + prize + "]";
	}

}
