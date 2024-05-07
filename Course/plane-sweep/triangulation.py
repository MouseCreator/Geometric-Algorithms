import matplotlib.pyplot as plt
import numpy as np


def read_triangulation_data(filename):
    with open(filename, 'r') as file:
        n, m = map(int, file.readline().split())

        points = np.zeros((n, 2))
        triangles = []

        for i in range(n):
            _, x, y = file.readline().split()
            points[i] = [float(x), float(y)]

        for j in range(m):
            triangle = list(map(int, file.readline().split()))
            triangles.append(triangle)

    return points, triangles


def plot_triangulation(points, triangles):
    plt.figure(figsize=(8, 6), dpi=200)

    plt.scatter(points[:, 0], points[:, 1], color='blue', s=5)

    for triangle in triangles:
        tris = points[triangle]
        tris = np.vstack([tris, tris[0]])
        plt.plot(tris[:, 0], tris[:, 1], 'k-', linewidth=0.5)

    plt.title('Triangulation Plot')
    plt.xlabel('X Coordinate')
    plt.ylabel('Y Coordinate')
    plt.axis('equal')
    plt.show()


filename = 't4.txt'
points, triangles = read_triangulation_data(filename)
plot_triangulation(points, triangles)
