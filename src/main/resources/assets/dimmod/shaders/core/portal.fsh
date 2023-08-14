#version 150

#moj_import <matrix.glsl>

uniform sampler2D Sampler0;
uniform sampler2D Sampler1;

uniform float GameTime;
uniform int EndPortalLayers;

in vec3 pos;
flat in vec3 flatPos;
flat in vec4 vColor;

out vec4 fragColor;

const float pos_infinity = 10000000000000.0;

const float PI = 3.1415926535897932384626433832795;

const float hoffset = 1.0;
const float voffset = 1.0;

float solve(in float m, in float b) {
	if (m == 0.0) {
		return 1/b;
	}
	else if (b * b + 4.0 * m >= 0.0) {
		float val = (sqrt(b * b + 4.0 * m) - b) / (2.0 * m);
		if (val > 0) {
			return val;
		}
	}
	return pos_infinity;
}

void main() {
	vec3 dir = normalize(pos);
	float dx = mod(pos.x - flatPos.x, 1.0);
	float dz = mod(pos.z - flatPos.z, 1.0);

	float dxb = dx + voffset * dir.x/dir.y;
	float dzb = dz + voffset * dir.z/dir.y;

	int px = int(255.0 * vColor.r);
	int pz = int(255.0 * vColor.g);
	int nx = int(255.0 * vColor.b);
	int nz = int(255.0 * vColor.a);

	float pxb = hoffset - float(px) - (1.0 - dxb);
	float pzb = hoffset - float(pz) - (1.0 - dzb);
	float nxb = hoffset - float(nx) - dxb;
	float nzb = hoffset - float(nz) - dzb;

	float mx = -dir.x/dir.y;
	float mz = -dir.z/dir.y;

	float ha = solve(mx, pxb);
	float hb = solve(mz, pzb);
	float hc = solve(-mx, nxb);
	float hd = solve(-mz, nzb);

	float h = min(min(ha,hb),min(hc,hd));
	fragColor = vec4(0.0, 0.0, sin(h*10 - GameTime * 1200 * PI) * exp(-h/20.0), sin(h*10) * exp(-h/20.0));
}
