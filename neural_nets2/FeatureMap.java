package neural_nets2;

import java.util.Random;

public class FeatureMap extends Layer{
	
	int maps;
	int detail_r;
	
	int inmap_r;
	
	int ftmap_r;
	
	int maxpool_r;
	
	double[][] map_z;
	double[][] map_a;
	double[][] map_d_z;
	
	private FeatureMap(int in, int out) {
		super(in, out);
	}
	
	static FeatureMap getMap(int in, int maps, int inmap_r, int detail_r, int maxpool_r) {
		int ftmap_r = inmap_r - detail_r + 1;
		int out = maps*ftmap_r*ftmap_r/maxpool_r/maxpool_r;
		
		FeatureMap map = new FeatureMap(in, out);
		map.maps = maps;
		map.detail_r = detail_r;
		map.inmap_r = inmap_r;
		map.ftmap_r = ftmap_r;
		map.maxpool_r = maxpool_r;
		
		map.biases = new double[maps];
		for(int i = 0; i<maps; i++) {
			map.biases[i] = new Random().nextGaussian();
		}
		map.d_biases = new double[maps];
		map.weights = new double[maps][detail_r*detail_r];
		for(int i = 0; i<maps; i++) {
			for(int j = 0; j<detail_r*detail_r; j++) {
				map.weights[i][j] = new Random().nextGaussian() / Math.sqrt(detail_r*detail_r);
			}
		}
		map.d_weights = new double[maps][detail_r*detail_r];
		
		map.map_z = new double[maps][ftmap_r*ftmap_r];
		map.map_d_z = new double[maps][ftmap_r*ftmap_r];
		map.map_a = new double[maps][ftmap_r*ftmap_r];
		
		return map;
	}
	
	void put(double[] inputs) {
		for(int i = 0; i<maps; i++) {
			for(int j = 0; j<ftmap_r*ftmap_r; j++) {
				map_z[i][j] = 0;
				int start_pos = j%ftmap_r + j/ftmap_r*inmap_r;
				for(int p = 0; p < detail_r*detail_r; p++) {
					int rel_pos = p%detail_r + p/detail_r*inmap_r;
					map_z[i][j] += inputs[start_pos+rel_pos] * weights[i][p];
				}
				map_z[i][j] += biases[i];
				map_a[i][j] = sigmoid(map_z[i][j]);
			}
		}
		for(int i = 0; i<z.length; i++) {
			a[i] = 0;
		}
		for(int map = 0; map<maps; map++) {
			for(int j = 0; j < outputs/maps; j++) {
				int x = j%ftmap_r/maxpool_r;
				int y = (j/ftmap_r)/maxpool_r;
				int p = map * outputs/maps + x + y*ftmap_r/maxpool_r;
				if(map_a[map][j] > a[p]) {
					a[p] = map_a[map][j];
					z[p] = map_z[map][j];
				}
			}
		}
	}
	
	void output_z_change(double[] y) {
		super.output_z_change(y);
		map_d_z();
	}
	
	
	void calc_z_change(double[] d_z, double[][] weights) {
		
		super.calc_z_change(d_z, weights);
		
		map_d_z();
		
	}
	
	void map_d_z() {
		for (int m = 0; m<maps; m++) {
			for(int i = 0; i<ftmap_r*ftmap_r; i++) {
				map_d_z[m][i] = 0;
			}
		}
		
		int n = 0;
		for (int m = 0; m<maps; m++) {
			for (int y = 0; y<ftmap_r; y+=maxpool_r) {
				for (int x = 0; x<ftmap_r; x+=maxpool_r) {
					int start_p = x + y*ftmap_r;
					double max_a = 0;
					int max_p = -1;
					for (int dy = 0; dy<maxpool_r; dy++) {
						for (int dx = 0; dx<maxpool_r; dx++) {
							int p = start_p + dx + dy*maxpool_r;
							System.out.println("lol");
							if(map_a[m][p] > max_a) {
								max_a = map_a[m][p];
								max_p = p;
							}
						}
					}
					map_d_z[m][max_p] = d_z[n++];
				}
			}
		}
	}
	
	void calc_d_wb(double[] a){
		int n = 0;
		for (int m = 0; m<maps; m++) {
			for (int i = 0; i<ftmap_r*ftmap_r; i++) {
				d_biases[m] += map_d_z[m][i];
			}
			for(int x = 0; x<ftmap_r; x++) {
				for(int y = 0; y<ftmap_r; y++) {
					for(int detail_x = 0; detail_x<detail_r; detail_x++) {
						for(int detail_y = 0; detail_y<detail_y; detail_y++) {
							d_weights[m][detail_x+detail_y*detail_r] += map_d_z[m][x+y*ftmap_r] * a[(x+detail_x) + (y+detail_y)*inmap_r];
						}
					}
				}
			}
		}
	}
	
	
}