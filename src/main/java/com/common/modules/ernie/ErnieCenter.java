package com.common.modules.ernie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * 摇奖中心
 * @Description: TODO
 * @ClassName: ErnieCenter 
 * @author huangzy@gzjp.cn
 * @date 2015年5月19日 下午3:49:56
 */
public class ErnieCenter<T> {
	private List<ProbEntity<T>> probEntityList = new ArrayList<ProbEntity<T>>();

	private List<Range> rangeList = new ArrayList<ErnieCenter<T>.Range>();

	private int max = 0;

	public void addProbEntity(ProbEntity<T> probEntity) {
		probEntityList.add(probEntity);
	}

	public void buid() {
		//一旦调用buid方法,就不再允许调用addProbEntity方法.
		probEntityList = Collections.unmodifiableList(probEntityList);

		int multiple = getMultiple(probEntityList);
		EnsureNotDecimals(probEntityList, multiple);

		int mix = 0;
		int tmpMax = 0;
		for (ProbEntity<T> probEntity : probEntityList) {
			tmpMax = probEntity.getProb().intValue() + mix;

			Range range = new Range(mix, tmpMax, probEntity);
			rangeList.add(range);

			mix = tmpMax;
		}
		this.max = tmpMax;
	}

	/**
	 * 开始摇奖
	 * @return
	 */
	public ProbEntity<T> ernie() {
		ProbEntity<T> probEntity = null;
		int rangeNum = new Random().nextInt(max) + 1;
		for (ErnieCenter<T>.Range range : rangeList) {
			boolean isInRange = range.isInRange(rangeNum);

			//System.out.println(max+","+rangeNum+","+isInRange);
			if (isInRange) {
				probEntity = range.getProbEntity();
				break;
			}
		}

		return probEntity;
	}

	/**
	 * 确保没有小数
	 * @param probEntityList
	 * @param multiple
	 */
	private void EnsureNotDecimals(List<ProbEntity<T>> probEntityList, int multiple) {
		for (ProbEntity probEntity : probEntityList) {
			Float newProb = probEntity.getProb() * multiple;
			probEntity.setProb(newProb);
		}
	}

	//返回倍数(取最长小数倍数*10)
	private int getMultiple(List<ProbEntity<T>> probEntityList) {
		int length = 0;
		for (ProbEntity probEntity : probEntityList) {
			String probStr = probEntity.getProb().toString();
			String[] arr = probStr.split("\\.");
			if (arr.length == 2) {
				int tmpLength = arr[1].length();
				// 谁大取谁
				length = tmpLength > length ? tmpLength : length;
			}
		}

		int ret = (int) Math.pow(10, length);

		return ret;
	}

	private class Range {
		private int rangeMin;
		private int rangeMax;

		private ProbEntity<T> probEntity;

		public Range(int rangeMin, int rangeMax, ProbEntity<T> probEntity) {
			super();
			this.rangeMin = rangeMin;
			this.rangeMax = rangeMax;
			this.probEntity = probEntity;
		}

		public boolean isInRange(int rangeNum) {
			boolean ret = false;
			if (this.rangeMin < rangeNum && rangeNum <= this.rangeMax) {
				ret = true;
			}
			return ret;
		}

		public ProbEntity<T> getProbEntity() {
			return probEntity;
		}

	}

}
