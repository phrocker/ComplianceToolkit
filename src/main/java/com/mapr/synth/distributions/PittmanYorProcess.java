/*
 * Licensed to the Ted Dunning under one or more contributor license
 * agreements.  See the NOTICE file that may be
 * distributed with this work for additional information
 * regarding copyright ownership.  Ted Dunning licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.mapr.synth.distributions;

import com.google.common.base.Preconditions;
import org.apache.mahout.common.RandomUtils;
import org.apache.mahout.math.list.DoubleArrayList;
import org.apache.mahout.math.random.Sampler;

import java.util.Random;

/**
 * Generates samples from a generalized or Pittman-Yor process.
 * <p>
 * The number of values drawn exactly once will asymptotically be equal to the discount parameter as the total number of
 * draws T increases without bound. The number of unique values sampled will increase as O(alpha * log T) if discount =
 * 0 or O(alpha * T^discount) for discount > 0.
 */
public final class PittmanYorProcess implements Sampler<Integer> {
    private double alpha;
    private double weight = 0;
    private double discount;
    private final DoubleArrayList weights = new DoubleArrayList();
    private final Random rand = RandomUtils.getRandom();

    /**
     * Constructs a Dirichlet process sampler. This is done by setting discount = 0.
     *
     * @param alpha
     *            The strength parameter for the Dirichlet process.
     */
    @SuppressWarnings("unused")
    public PittmanYorProcess(double alpha) {
        this(alpha, 0);
    }

    /**
     * Constructs a Pitman-Yor sampler.
     *
     * @param alpha
     *            The strength parameter that drives the number of unique values as a function of draws.
     * @param discount
     *            The discount parameter that drives the percentage of values that occur once in a large sample.
     */
    public PittmanYorProcess(double alpha, double discount) {
        Preconditions.checkArgument(alpha > 0);
        Preconditions.checkArgument(discount >= 0 && discount <= 1);
        this.alpha = alpha;
        this.discount = discount;
    }

    @Override
    public synchronized Integer sample() {
        double u = rand.nextDouble() * (alpha + weight);
        for (int j = 0; j < weights.size(); j++) {
            // select existing options with probability (w_j - d) / (alpha + w)
            if (u < weights.get(j) - discount) {
                weights.set(j, weights.get(j) + 1);
                weight++;
                return j;
            } else {
                u -= weights.get(j) - discount;
            }
        }

        // if no existing item selected, pick new item with probability (alpha - d*t) / (alpha + w)
        // where t is number of pre-existing cases
        weights.add(1);
        weight++;
        return weights.size() - 1;
    }

    /**
     * @return the number of unique values that have been returned.
     */
    @SuppressWarnings("unused")
    public int size() {
        return weights.size();
    }

    /**
     * @return the number draws so far.
     */
    @SuppressWarnings("unused")
    public int count() {
        return (int) weight;
    }

    /**
     * @param j
     *            Which value to test.
     *
     * @return The number of times that j has been returned so far.
     */
    @SuppressWarnings("unused")
    public int count(int j) {
        Preconditions.checkArgument(j >= 0);

        if (j < weights.size()) {
            return (int) weights.get(j);
        } else {
            return 0;
        }
    }

    public void setCount(int term, double count) {
        while (weights.size() <= term) {
            weights.add(0);
        }
        weight += (count - weights.get(term));
        weights.set(term, count);
    }

    public void setSeed(long seed) {
        rand.setSeed(seed);
    }

    public void setAlpha(double alpha) {
        Preconditions.checkArgument(alpha > 0);
        this.alpha = alpha;
    }

    public void setDiscount(double discount) {
        Preconditions.checkArgument(discount >= 0 && discount <= 1);
        this.discount = discount;
    }
}
